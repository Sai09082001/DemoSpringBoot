package com.example.demo.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.entity.Orders;


public interface OrdersRepo extends JpaRepository<Orders, Integer> {
	
	
	@Query("SELECT o FROM Orders o WHERE o.createdAt >= :x")
	List<Orders> searchByDate(@Param("x") Date s);
	
	@Query("SELECT o FROM Orders o WHERE o.user.id = :x")
	List<Orders> findByUserId(@Param("x") int userId);
	
	@Query("SELECT o FROM Orders o WHERE o.shipper.id = :x")
	List<Orders> findByShipperId(@Param("x") int shipperId);
		/// Đếm số lượng đơn group by MONTH(buyDate)
		// - dùng custom object để build
		// SELECT id, MONTH(buyDate) from bill;
		// select count(*), MONTH(buyDate) from bill
		// group by MONTH(buyDate)
	@Query("SELECT count(b.id), month(b.createdAt), year(b.createdAt) "
			+ "FROM Orders b GROUP BY month(b.createdAt), year(b.createdAt) ")
	List<Object[]> thongKeBill();

//	@Query("SELECT new com.example.demo.dto.OrderStatisticDTO(count(b.id), '/') "
//				+ " FROM Orders b GROUP BY month(b.createdAt), year(b.createdAt) ")
//	List<OrderStatisticDTO> thongKeBill2();

}