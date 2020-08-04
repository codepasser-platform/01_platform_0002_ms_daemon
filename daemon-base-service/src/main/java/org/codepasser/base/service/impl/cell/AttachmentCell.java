package org.codepasser.base.service.impl.cell;

import com.google.common.collect.Lists;

import org.codepasser.base.dao.repository.AttachmentMapRepository;
import org.codepasser.base.dao.repository.AttachmentRepository;
import org.codepasser.base.model.entity.Attachment;
import org.codepasser.base.model.entity.AttachmentMap;
import org.codepasser.base.service.basement.vo.AttachmentDetail;
import org.codepasser.common.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import static com.google.common.collect.Sets.newHashSet;
import static org.codepasser.common.model.entity.inner.State.DELETED;
import static org.codepasser.common.model.entity.inner.State.EXPIRED;
import static org.codepasser.common.service.exception.NotFoundException.Error.DATA;

/**
 * AttachmentCell.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/26 : base version.
 */
@Component
public class AttachmentCell {

  @Autowired private AttachmentRepository attachmentRepository;
  @Autowired private AttachmentMapRepository attachmentMapRepository;

  /**
   * 查询所有附件数据.
   *
   * @param metaId 源数据ID
   * @return 附件数据
   */
  @Nonnull
  public List<AttachmentDetail> findByMetaId(@Nonnull Long metaId) {
    List<AttachmentDetail> attachmentDetails = Lists.newArrayList();
    List<AttachmentMap> maps =
        attachmentMapRepository.findAllByMetaIdAndStateNotIn(metaId, newHashSet(DELETED, EXPIRED));
    if (!maps.isEmpty()) {
      List<Long> ids =
          maps.stream().map(AttachmentMap::getAttachmentId).collect(Collectors.toList());
      List<Attachment> attachments =
          attachmentRepository.findAllByIdInAndStateNotIn(ids, newHashSet(DELETED, EXPIRED));

      if (!attachments.isEmpty()) {
        attachmentDetails =
            attachments.stream()
                .map((item) -> new AttachmentDetail().from(item))
                .collect(Collectors.toList());
      }
    }

    return attachmentDetails;
  }

  /**
   * 查询附件数据.
   *
   * @param attachmentId 源数据ID
   * @return 附件数据
   */
  @Nonnull
  public AttachmentDetail validById(@Nonnull Long attachmentId) {
    Attachment attachment =
        attachmentRepository
            .findAllByIdAndStateNotIn(attachmentId, newHashSet(DELETED, EXPIRED))
            .orElseThrow(() -> new NotFoundException(DATA));
    return new AttachmentDetail().from(attachment);
  }
}
