package org.codepasser.base.service.basement;

import org.codepasser.base.dao.repository.AttachmentMapRepository;
import org.codepasser.base.dao.repository.AttachmentRepository;
import org.codepasser.base.model.entity.Attachment;
import org.codepasser.base.model.entity.AttachmentMap;
import org.codepasser.base.model.entity.builder.AttachmentBuilder;
import org.codepasser.base.model.entity.inner.AttachmentCategory;
import org.codepasser.base.model.entity.inner.AttachmentStatus;
import org.codepasser.base.service.basement.vo.AttachmentDetail;
import org.codepasser.base.service.impl.cell.AttachmentCell;
import org.codepasser.common.service.exception.NotFoundException;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import static com.google.common.collect.Sets.newHashSet;
import static org.codepasser.common.model.entity.inner.State.DELETED;
import static org.codepasser.common.model.entity.inner.State.EXPIRED;
import static org.codepasser.common.service.exception.NotFoundException.Error.DATA;

/**
 * AttachmentServiceImpl.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Service
@RestController
public class AttachmentServiceImpl implements AttachmentService {

  @Autowired private AttachmentRepository attachmentRepository;
  @Autowired private AttachmentMapRepository attachmentMapRepository;

  @Autowired private AttachmentCell attachmentCell;

  @Transactional
  @Nonnull
  @Override
  public Long saveAttachmentData(
      @RequestParam(value = "userId") Long userId,
      @RequestParam(value = "metaId") Long metaId,
      @RequestParam(value = "metaName") String metaName,
      @RequestParam(value = "category") AttachmentCategory category,
      @RequestParam(value = "directory") String directory,
      @RequestParam(value = "path") String path,
      @RequestParam(value = "url") String url,
      @RequestParam(value = "uri") String uri,
      @RequestParam(value = "size") Long size)
      throws ServiceException {
    Attachment attachment =
        AttachmentBuilder.attachmentBuilder()
            .createTime(new Date())
            .createUser(userId)
            .name(metaName)
            .category(category)
            .directory(directory)
            .path(path)
            .url(url)
            .uri(uri)
            .size(size)
            .build();
    AttachmentMap attachmentMap = new AttachmentMap();
    attachmentMap.setCreateTime(new Date());
    attachmentMap.setCreateUser(userId);
    attachmentMap.setMetaId(metaId);
    attachmentMap.setAttachmentId(attachment.getId());
    attachmentMap.setCategory(category);
    attachmentRepository.save(attachment);
    attachmentMapRepository.save(attachmentMap);
    return attachment.getId();
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse updateAttachmentStatus(
      @RequestParam(value = "id") Long id, @RequestParam(value = "status") AttachmentStatus status)
      throws ServiceException {
    Attachment attachment =
        attachmentRepository.findById(id).orElseThrow(() -> new NotFoundException(DATA));
    attachment.setStatus(status);
    attachmentRepository.save(attachment);
    return new AssertResponse(true);
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse updateAttachmentPersisted(
      @RequestParam(value = "id") Long id, @RequestParam(value = "size") Long size)
      throws ServiceException {
    Attachment attachment =
        attachmentRepository.findById(id).orElseThrow(() -> new NotFoundException(DATA));
    attachment.setStatus(AttachmentStatus.PERSISTED);
    attachment.setSize(size);
    attachmentRepository.save(attachment);
    return new AssertResponse(true);
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse attachmentExpired(@RequestParam(value = "metaId") Long metaId)
      throws ServiceException {

    List<AttachmentMap> maps =
        attachmentMapRepository.findAllByMetaIdAndStateNotIn(metaId, newHashSet(DELETED, EXPIRED));
    if (!maps.isEmpty()) {

      List<Long> ids =
          maps.stream()
              .map(
                  (map) -> {
                    map.setState(EXPIRED);
                    attachmentMapRepository.save(map);
                    return map.getAttachmentId();
                  })
              .collect(Collectors.toList());

      List<Attachment> attachments =
          attachmentRepository.findAllByIdInAndStateNotIn(ids, newHashSet(DELETED, EXPIRED));
      if (!attachments.isEmpty()) {
        attachments.forEach(
            (item) -> {
              item.setState(EXPIRED);
              attachmentRepository.save(item);
            });
      }
    }

    return new AssertResponse(true);
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse attachmentDeleted(@RequestParam(value = "metaId") Long metaId)
      throws ServiceException {

    List<AttachmentMap> maps =
        attachmentMapRepository.findAllByMetaIdAndStateNotIn(metaId, newHashSet(DELETED, EXPIRED));
    if (!maps.isEmpty()) {

      List<Long> ids =
          maps.stream()
              .map(
                  (map) -> {
                    map.setState(DELETED);
                    attachmentMapRepository.save(map);
                    return map.getAttachmentId();
                  })
              .collect(Collectors.toList());

      List<Attachment> attachments =
          attachmentRepository.findAllByIdInAndStateNotIn(ids, newHashSet(DELETED, EXPIRED));
      if (!attachments.isEmpty()) {
        attachments.forEach(
            (item) -> {
              item.setState(DELETED);
              attachmentRepository.save(item);
            });
      }
    }

    return new AssertResponse(true);
  }

  @Nonnull
  @Override
  public AttachmentDetail findAttachmentById(@PathVariable(value = "id") Long id)
      throws ServiceException {
    return attachmentCell.validById(id);
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse attachmentDeleteById(
      @RequestParam(value = "attachmentId") Long attachmentId) throws ServiceException {
    Attachment attachment =
        attachmentRepository.findById(attachmentId).orElseThrow(() -> new NotFoundException(DATA));
    attachment.setState(DELETED);
    attachmentRepository.save(attachment);
    return new AssertResponse(true);
  }
}
