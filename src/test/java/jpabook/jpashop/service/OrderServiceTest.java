package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문(){
        //given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createBook("JPA-BOOK", 10000, 10);

        int count =2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), count);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품주문시 상태는 ORDER", OrderStatus.ORDER,getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야한다",1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격*수량이다",10000*count,getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다",8,book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문초과(){
        //given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item item = createBook("JPA-BOOK", 10000, 10);

        int orderCount = 11;
        //when
        orderService.order(member.getId(), item.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야합니다.");
    }

    @Test
    public void 주문취소() {
        //given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item item = createBook("JPA-BOOK", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.orderCancel(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 주문 상태는 CANCEL 이다.",OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals("주문 취소 시 재고가 원복되어야한다.",10,item.getStockQuantity());
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}