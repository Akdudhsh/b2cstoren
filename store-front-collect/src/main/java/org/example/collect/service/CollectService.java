package org.example.collect.service;

import org.example.param.CollectListParam;
import org.example.param.CollectSaveParam;
import org.example.utils.R;

/**
 * @Author:罗蓉鑫
 * @Date: 2023/12/21 10:10
 * @Description:
 * @Version 1.0
 */
public interface CollectService {
    R save(CollectSaveParam collectSaveParam);

    R list(Integer userId);

    R remove(CollectSaveParam collectSaveParam);
}
