package com.jpabook.jpashop;

import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.item.Book;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember();
            em.persist(member);

            Book book1 = new Book();
            book1.setName("JPA1 Book");
            book1.setPrice(10000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("JPA2 Book");
            book2.setPrice(20000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem oderItem1 = OrderItem.createOderItem(book1, 10000, 1);
            OrderItem oderItem2 = OrderItem.createOderItem(book2, 20000, 2);

            Order order = createDelivery(member, oderItem1, oderItem2);
            em.persist(order);

        }

        private static Member createMember() {
            Member member = new Member();
            member.setName("김찬호");
            member.setAddress(new Address("김해", "2400", "1111"));
            return member;
        }

        public void dbInit2() {
            Member member = new Member();
            member.setName("정우혁");
            member.setAddress(new Address("정왕", "1", "2323"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName("Spring1 Book");
            book1.setPrice(20000);
            book1.setStockQuantity(200);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("Spring2 Book");
            book2.setPrice(40000);
            book2.setStockQuantity(300);
            em.persist(book2);

            OrderItem oderItem1 = OrderItem.createOderItem(book1, 20000, 3);
            OrderItem oderItem2 = OrderItem.createOderItem(book2, 40000, 4);

            Order order = createDelivery(member, oderItem1, oderItem2);
            em.persist(order);

        }

        private static Order createDelivery(Member member, OrderItem oderItem1, OrderItem oderItem2) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, oderItem1, oderItem2);
            return order;
        }
    }
}
