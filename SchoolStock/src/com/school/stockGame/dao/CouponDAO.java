package com.school.stockGame.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.school.stockGame.vo.CouponVO;

public class CouponDAO {


    public List<CouponVO> getCouponList() {
        List<CouponVO> list = new ArrayList<>();
        try (
             Connection conn = DBCP.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
            		 CouponQuery.GET_COUPON_LIST);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new CouponVO(
                    rs.getInt("coupon_no"), 
                    rs.getString("name"), 
                    rs.getInt("price")
                ));
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return list;
    }
}
