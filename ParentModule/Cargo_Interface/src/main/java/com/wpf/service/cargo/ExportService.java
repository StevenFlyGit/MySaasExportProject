package com.wpf.service.cargo;

import com.wpf.domain.cargo.Export;
import com.wpf.domain.cargo.ExportExample;
import com.github.pagehelper.PageInfo;


public interface ExportService {

    Export findById(String id);

    void save(Export export);

    void update(Export export);

    void delete(String id);

	PageInfo<Export> findByPage(ExportExample example, int pageNum, int pageSize);
}
