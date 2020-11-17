package com.wpf.service.cargo;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.cargo.Export;
import com.wpf.domain.cargo.ExportExample;
import com.wpf.vo.ExportResult;


public interface ExportService {

    Export findById(String id);

    void save(Export export);

    void update(Export export);

    void delete(String id);

	PageInfo<Export> findByPage(ExportExample example, int pageNum, int pageSize);

    void updateExportFromRemote(ExportResult exportResult);
}
