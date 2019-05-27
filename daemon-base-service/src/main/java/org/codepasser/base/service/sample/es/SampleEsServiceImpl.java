package org.codepasser.base.service.sample.es;

import static org.codepasser.common.service.exception.NotFoundException.Error.DATA;
import static org.codepasser.common.service.helper.PagedDataUtils.rePage;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nonnull;
import org.codepasser.base.dao.repository.es.sample.SampleManualsRepository;
import org.codepasser.base.model.es.sample.SampleManual;
import org.codepasser.base.model.es.sample.builder.SampleManualBuilder;
import org.codepasser.base.model.es.sample.dto.SampleManualDto;
import org.codepasser.common.service.exception.NotFoundException;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.codepasser.common.service.response.PagedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleEsServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
@Service
@RestController
public class SampleEsServiceImpl implements SampleEsService {

  @Autowired private SampleManualsRepository sampleManualsRepository;

  @Nonnull
  @Override
  public AssertResponse creationDocument(@Nonnull @RequestParam("name") String name)
      throws ServiceException {

    sampleManualsRepository.save(mockData(1, name, "S01" + "2018" + "300KM普通版" + "普通版"));
    sampleManualsRepository.save(mockData(2, name, "S01" + "2018" + "380KM普通版" + "PRO版"));
    sampleManualsRepository.save(mockData(3, name, "S01" + "2018" + "300KM普通版" + "普通版"));
    sampleManualsRepository.save(mockData(4, name, "S01" + "2018" + "380KM普通版" + "PRO版"));

    return AssertResponse.success();
  }

  @Nonnull
  @Override
  public AssertResponse deletionDocument(@Nonnull @RequestParam("name") String category)
      throws ServiceException {
    //    sampleManualsRepository.deleteById();
    sampleManualsRepository.deleteAll();
    return AssertResponse.success();
  }

  @Nonnull
  @Override
  public List<SampleManualDto> all() throws ServiceException {
    List<SampleManualDto> manuals = Lists.newArrayList();
    sampleManualsRepository
        .findAll()
        .forEach((item) -> manuals.add(new SampleManualDto().from(item)));
    return manuals;
  }

  @Nonnull
  @Override
  public List<SampleManualDto> allSort() throws ServiceException {
    List<SampleManualDto> manuals = Lists.newArrayList();
    sampleManualsRepository
        .findAll(Sort.by(Sort.Order.desc("createTime")))
        .forEach((item) -> manuals.add(new SampleManualDto().from(item)));
    return manuals;
  }

  @Nonnull
  @Override
  public PagedData<SampleManualDto> allPageable() throws ServiceException {
    // 0 <= page <= pageTotal
    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("createTime")));
    Page<SampleManual> manualPage = sampleManualsRepository.findAll(pageable);
    return rePage(manualPage, pageable, item -> new SampleManualDto().from(item));
  }

  @Nonnull
  @Override
  public SampleManualDto find(@Nonnull @PathVariable("id") String id) throws ServiceException {
    SampleManual manual =
        sampleManualsRepository.findById(id).orElseThrow(() -> new NotFoundException(DATA));
    return new SampleManualDto().from(manual);
  }

  @Nonnull
  @Override
  public List<SampleManualDto> find(@Nonnull String[] ids) throws ServiceException {
    List<String> idList = Lists.newArrayList(ids);
    List<SampleManualDto> manuals = Lists.newArrayList();
    sampleManualsRepository
        .findAllById(idList)
        .forEach((item) -> manuals.add(new SampleManualDto().from(item)));
    return manuals;
  }

  private SampleManual mockData(int index, String name, String keyContent) {
    SampleManualBuilder builder =
        SampleManualBuilder.build()
            .setName(name + index)
            .setContent(keyContent)
            .setManualPath("/data/storage/manual/" + name + "/" + index + ".html")
            .setManualUrl(
                "http://www.codepasser.com/storage/manual/" + name + "/" + index + ".html");
    return builder.get();
  }
}
