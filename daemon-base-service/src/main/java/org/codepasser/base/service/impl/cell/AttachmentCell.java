package org.codepasser.base.service.impl.cell;

import static com.google.common.collect.Sets.newHashSet;
import static org.codepasser.common.model.entity.inner.State.DELETED;
import static org.codepasser.common.model.entity.inner.State.EXPIRED;
import static org.codepasser.common.service.exception.NotFoundException.Error.DATA;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.codepasser.base.dao.repository.AttachmentMapRepository;
import org.codepasser.base.dao.repository.AttachmentRepository;
import org.codepasser.base.model.entity.Attachment;
import org.codepasser.base.model.entity.AttachmentMap;
import org.codepasser.base.service.basement.vo.AttachmentItem;
import org.codepasser.common.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
  public List<AttachmentItem> findByMetaId(@Nonnull Long metaId) {
    List<AttachmentItem> attachmentItems = Lists.newArrayList();
    List<AttachmentMap> maps =
        attachmentMapRepository.findAllByMetaIdAndStateNotIn(metaId, newHashSet(DELETED, EXPIRED));
    if (!maps.isEmpty()) {
      List<Long> ids =
          maps.stream().map(AttachmentMap::getAttachmentId).collect(Collectors.toList());
      List<Attachment> attachments =
          attachmentRepository.findAllByIdInAndStateNotIn(ids, newHashSet(DELETED, EXPIRED));

      if (!attachments.isEmpty()) {
        attachmentItems =
            attachments.stream()
                .map((item) -> new AttachmentItem().from(item))
                .collect(Collectors.toList());
      }
    }

    return attachmentItems;
  }

  /**
   * 查询附件数据.
   *
   * @param attachmentId 源数据ID
   * @return 附件数据
   */
  @Nonnull
  public AttachmentItem validById(@Nonnull Long attachmentId) {
    Attachment attachment =
        attachmentRepository
            .findAllByIdAndStateNotIn(attachmentId, newHashSet(DELETED, EXPIRED))
            .orElseThrow(() -> new NotFoundException(DATA));
    return new AttachmentItem().from(attachment);
  }
}
