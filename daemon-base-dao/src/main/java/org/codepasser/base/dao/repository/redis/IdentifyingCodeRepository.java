package org.codepasser.base.dao.repository.redis;

import org.codepasser.base.model.business.category.IdentifyingCodeType;
import org.codepasser.common.exception.CommonException;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.codepasser.common.exception.CommonException.Error.IDENTIFYING_CODE_CYCLE_ERROR;

/**
 * IdentifyingCodeRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public class IdentifyingCodeRepository {

  private static final String CACHE_IDENTIFYING_CODES =
      "DAEMON:BASE:CACHE:IDENTIFYING_CODES:%s:%s:%s";
  private static final String CACHE_IDENTIFYING_OWNER = "DAEMON:BASE:CACHE:IDENTIFYING_OWNER:%s";
  @InjectLogger private Logger logger;
  @Autowired private StringRedisTemplate template;

  /**
   * 索取认证码.
   *
   * @param target 目标
   * @param identifyingCodeType 认证码类型
   * @param hashValues 认证记录
   * @param timeout 超时时间
   * @param unit 时间单位
   */
  public String claimIdentifyingCode(
      String target,
      IdentifyingCodeType identifyingCodeType,
      Map<String, String> hashValues,
      long timeout,
      TimeUnit unit)
      throws CommonException {
    if (!isExceedDuration(target, identifyingCodeType)) {
      throw new CommonException(IDENTIFYING_CODE_CYCLE_ERROR);
    }
    String identifyingCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    resetOwnerIdentifyingCode(target, identifyingCodeType, timeout, unit, identifyingCode);
    saveIdentifyingCode(target, identifyingCodeType, hashValues, timeout, unit, identifyingCode);
    return identifyingCode;
  }

  /**
   * 赎回认证码.
   *
   * @param identifyingCodeType 认证类型
   * @param target 目标
   * @param identifyingCode 认证码
   */
  public void redeemIdentifyingCode(
      IdentifyingCodeType identifyingCodeType, String target, String identifyingCode) {
    String key = generateIdentifyingCodeKey(identifyingCodeType, target, identifyingCode);
    if (template.hasKey(key)) {
      HashOperations<String, String, String> operations = template.opsForHash();
      String owner = operations.get(key, "_owner");
      if (owner != null) {
        operations.delete(generateOwnerKey(owner), identifyingCodeType.toString());
      }
      template.delete(key);
    }
  }

  /**
   * 认证码校验.
   *
   * @param identifyingCodeType 认证类型
   * @param target 目标
   * @param identifyingCode 认证码
   * @return 校验结果
   */
  public boolean isIdentifyingCodeValidate(
      IdentifyingCodeType identifyingCodeType, String target, String identifyingCode) {
    String key = generateIdentifyingCodeKey(identifyingCodeType, target, identifyingCode);
    if (template.hasKey(key)) {
      Long expire = template.getExpire(key);
      return expire > 0;
    }
    return false;
  }

  /**
   * 查询认证码.
   *
   * @param target
   * @param identifyingCodeType
   * @return identifyingCode
   */
  public String findIdentifyingCode(String target, IdentifyingCodeType identifyingCodeType) {
    HashOperations<String, String, String> hashOperations = template.opsForHash();
    String ownerKey = generateOwnerKey(target);
    return hashOperations.get(ownerKey, identifyingCodeType.toString());
  }

  /**
   * 认证码获取频率校验(间隔大于一分钟重新分配认证码).
   *
   * @param target 电话
   * @param identifyingCodeType 认证类型
   * @return 校验结果
   */
  private boolean isExceedDuration(String target, IdentifyingCodeType identifyingCodeType) {
    HashOperations<String, String, String> hashOperations = template.opsForHash();
    String ownerKey = generateOwnerKey(target);
    String previousIdentifyingCode = hashOperations.get(ownerKey, identifyingCodeType.toString());

    String key = generateIdentifyingCodeKey(identifyingCodeType, target, previousIdentifyingCode);
    HashOperations<String, Object, Object> hashOperationsForValidate = template.opsForHash();
    Map<Object, Object> map = hashOperationsForValidate.entries(key);
    if (!map.isEmpty()) {
      long duration = new Date().getTime() - (Long.parseLong(map.get("issue_time").toString()));
      return duration >= 60 * 1000;
    }
    return true;
  }

  /**
   * 重置认证码.
   *
   * @param target 目标
   * @param identifyingCodeType 认证类型
   * @param timeout 超时时间
   * @param unit 时间单位
   * @param identifyingCode 认证码
   */
  private void resetOwnerIdentifyingCode(
      String target,
      IdentifyingCodeType identifyingCodeType,
      long timeout,
      TimeUnit unit,
      String identifyingCode) {
    HashOperations<String, String, String> hashOperations = template.opsForHash();
    String ownerKey = generateOwnerKey(target);

    String previousIdentifyingCode = hashOperations.get(ownerKey, identifyingCodeType.toString());
    template.delete(
        generateIdentifyingCodeKey(identifyingCodeType, target, previousIdentifyingCode));
    hashOperations.put(ownerKey, identifyingCodeType.toString(), identifyingCode);
    template.expire(ownerKey, timeout, unit);
  }

  /**
   * 缓存校验码.
   *
   * @param target 目标
   * @param identifyingCodeType 认证类型
   * @param hashValues 认证记录
   * @param timeout 超时时间
   * @param unit 时间单位
   * @param identifyingCode 认证码
   */
  private void saveIdentifyingCode(
      String target,
      IdentifyingCodeType identifyingCodeType,
      Map<String, String> hashValues,
      long timeout,
      TimeUnit unit,
      String identifyingCode) {
    String key = generateIdentifyingCodeKey(identifyingCodeType, target, identifyingCode);
    HashOperations<String, Object, Object> hashOperations = template.opsForHash();
    hashOperations.putAll(key, hashValues);
    hashOperations.put(key, "_owner", target);
    template.expire(key, timeout, unit);
  }

  /**
   * 生成认证码所有者缓存key.
   *
   * @param target 电话号
   * @return 认证码所有者缓存key
   */
  private String generateOwnerKey(String target) {
    return String.format(CACHE_IDENTIFYING_OWNER, target);
  }

  /**
   * 生成认证码缓存key.
   *
   * @param identifyingCodeType 认证类型
   * @param target 目标
   * @param identifyingCode 认证码
   * @return 认证码缓存key
   */
  private String generateIdentifyingCodeKey(
      IdentifyingCodeType identifyingCodeType, String target, String identifyingCode) {
    return String.format(CACHE_IDENTIFYING_CODES, identifyingCodeType, target, identifyingCode);
  }
}
