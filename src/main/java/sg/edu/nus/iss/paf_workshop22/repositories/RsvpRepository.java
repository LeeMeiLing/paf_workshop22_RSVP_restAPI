package sg.edu.nus.iss.paf_workshop22.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_workshop22.models.Rsvp;

@Repository
public class RsvpRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String countSQL = "select count(*) from rsvp";
    private final String selectAllSQL = "select * from rsvp";
    private final String insertSQL = "insert into rsvp (fullname, email, phone, confirmation_date, comments) values (?, ?, ?, ?, ?)";
    private final String selectByIdSQL = "select * from rsvp where id = ?";
    // private final String selectByNameSQL = "select * from rsvp where fullname like \' % ? % \' "; // dont do this !!
    private final String selectByNameSQL = "select * from rsvp where fullname like ?";
    private final String selectByEmailSQL = "select * from rsvp where email = ?";
    private final String updateSQL = 
            "update rsvp set fullname = ?, email = ?, phone = ?, confirmation_date = ?, comments = ? where email = ?";


    // Get number of rsvp
    public Integer countAll() {
        return jdbcTemplate.queryForObject(countSQL, Integer.class);
    }

    // Get All rsvp
    public List<Rsvp> findAll() {
        return jdbcTemplate.query(selectAllSQL, BeanPropertyRowMapper.newInstance(Rsvp.class));
    }

    // Get rsvp by Id
    public Optional<Rsvp> findById(Integer id) {

        Rsvp rsvp = null;

        try {

            rsvp = jdbcTemplate.queryForObject(selectByIdSQL, BeanPropertyRowMapper.newInstance(Rsvp.class), id);

            if (null == rsvp) {
                return Optional.empty();
            } else {
                return Optional.of(rsvp);
            }

        } catch (Exception ex) {

            return Optional.empty();

        }
    }

    // Get rsvp by Email
    public Optional<Rsvp> findByEmail(String email) {

        Rsvp rsvp = null;

        try {

            rsvp = jdbcTemplate.queryForObject(selectByEmailSQL, BeanPropertyRowMapper.newInstance(Rsvp.class), email);

            if (null == rsvp) {
                System.out.println("rsvp not found");
                return Optional.empty();
            } else {
                return Optional.of(rsvp);
            }

        } catch (Exception ex) {

            // IncorrectResultSizeDataAccessException: Incorrect result size: expected 1, actual 5
            // when using queryForObject, if found more than one result, it will raise exception
            if (null == rsvp) {
                return Optional.empty();
            } else {
                throw ex;
            }
        }
    }

    // Get all rsvp records that match the given name
    public List<Rsvp> findByName(String name) {

        List<Rsvp> rsvpList = new ArrayList<>();
        rsvpList = jdbcTemplate.query(selectByNameSQL, new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "%" + name + "%");
            }
            
        }, BeanPropertyRowMapper.newInstance(Rsvp.class));

        return rsvpList;

    }

    // add new rsvp
    public Boolean save(Rsvp rsvp) {

        Integer result = jdbcTemplate.update(insertSQL, rsvp.getFullname(), rsvp.getEmail(), rsvp.getPhone(),
                rsvp.getConfirmationDate(), rsvp.getComments());

        return result > 0 ? true : false;
    }

    // update rsvp by Email
    public Boolean update(Rsvp rsvp) {

        Integer result = jdbcTemplate.update(updateSQL, rsvp.getFullname(), rsvp.getEmail(), rsvp.getPhone(),
                rsvp.getConfirmationDate(), rsvp.getComments(), rsvp.getEmail());

        return result > 0 ? true : false;

    }

    public int[] batchUpdate(List<Rsvp> rsvps) {

        return jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                ps.setString(1, rsvps.get(i).getFullname());
                ps.setString(2, rsvps.get(i).getEmail());
                ps.setString(3, rsvps.get(i).getPhone());
                ps.setDate(4, rsvps.get(i).getConfirmationDate());
                ps.setString(5, rsvps.get(i).getComments());

            }

            @Override
            public int getBatchSize() {
                return rsvps.size();
            }

        });

    }

}
