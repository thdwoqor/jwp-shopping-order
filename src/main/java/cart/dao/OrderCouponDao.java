package cart.dao;

import cart.entity.CouponEntity;
import cart.entity.MemberCouponEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrderCouponDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public OrderCouponDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("order_coupon")
                .usingGeneratedKeyColumns("id");
    }

    private static RowMapper<MemberCouponEntity> rowMapper() {
        return (rs, rowNum) -> new MemberCouponEntity(rs.getLong("member_coupon.id"),
                new CouponEntity(
                        rs.getLong("coupon.id"),
                        rs.getString("coupon.name"),
                        rs.getString("coupon.discount_type"),
                        rs.getInt("amount")
                ),
                rs.getBoolean("used"));
    }

    public Long create(Long orderItemId, Long memberCouponId) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("order_item_id", orderItemId)
                .addValue("member_coupon_id", memberCouponId);

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<MemberCouponEntity> findByOrderItemId(Long orderItemId) {
        try {
            String sql = "SELECT * FROM order_coupon, member_coupon, coupon WHERE order_coupon.member_coupon_id = member_coupon.id AND member_coupon.coupon_id = coupon.id AND order_item_id = ?";
            return jdbcTemplate.query(sql, rowMapper(), orderItemId);
        } catch (final EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }
}
