package com.upliv.user_service.repository;

import com.upliv.user_service.model.staff.attendance.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface StaffAttendanceRepository extends MongoRepository<Attendance, UUID> {
//
//    @Query("{'StaffId':'?0', 'DailyAttendance.DateForAttendance':'?0'}")
//    public DailyAttendance findDailyAttendance(UUID staffId, Date dateForAttendance);
//
//    @Query("{'IsActive':true}")
//    List<Attendance> findAttendanceOfActiveStaff(Pageable pageable);
//
//    @Query("{'StaffId':'?0','DailyAttendance.AttendanceFlag':'PRESENT', 'DailyAttendance.Attendance':'APPROVED_LEAVE', , 'DailyAttendance.Attendance':'NATIONAL_HOLIDAY'}")
//    public Attendance findForPercentage(UUID id, Month month);
//
//    @Query("{'StaffId':'?0', 'IsActive' : '?0'}")
//    public Optional<Attendance> findByActiveStaff(UUID staffId, StaffConstants.EMPLOYMENT_STATUS status);
//
//    @Query("{'DailyAttendance.DateForAttendance' : '?0'}")
//    public List<DailyAttendance> findIfDateExists(Date dateForAttendance);

}