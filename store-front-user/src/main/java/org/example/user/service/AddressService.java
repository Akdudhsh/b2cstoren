package org.example.user.service;

import org.example.param.AddressListParam;
import org.example.pojo.Address;
import org.example.utils.R;

/**
 * @Author:罗蓉鑫
 * @Date: 2023/12/19 10:02
 * @Description:
 * @Version 1.0
 */
public interface AddressService {
    R list(AddressListParam userIdParam);

    R save(Address address);

    R remove(Long id);
}
