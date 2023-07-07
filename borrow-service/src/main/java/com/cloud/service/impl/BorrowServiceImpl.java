package com.cloud.service.impl;

import com.cloud.dto.UserBorrowDetail;
import com.cloud.mapper.BorrowMapper;
import com.cloud.service.BorrowService;
import com.entity.Book;
import com.entity.Borrow;
import com.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service("borrowService")
public class BorrowServiceImpl implements BorrowService {

    @Resource
    private BorrowMapper borrowMapper;
    @Resource
    private RestTemplate template;

    @Override
    public UserBorrowDetail getUserBorrowDetailByUid(Integer uid) {

        List<Borrow> borrow = borrowMapper.getBorrowByUid(uid);

        User user = template.getForObject("http://userservice/api/user/" + uid, User.class);
        List<Book> bookList = borrow.stream()
                .map(b -> template.getForObject("http://bookservice/api/book/" + b.getBid(), Book.class))
                .collect(Collectors.toList());
        return new UserBorrowDetail(user, bookList);

    }

}
