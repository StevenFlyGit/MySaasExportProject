package com.wpf.service.cargo;


import com.wpf.domain.cargo.ExportProduct;
import com.wpf.domain.cargo.ExportProductExample;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface ExportProductService {

	ExportProduct findById(String id);

	void save(ExportProduct exportProduct);

	void update(ExportProduct exportProduct);

	void delete(String id);

	PageInfo<ExportProduct> findByPage(ExportProductExample exportProductExample, int pageNum, int pageSize);

    List<ExportProduct> findAll(ExportProductExample exportProductExample);

}
