package com.sgu.MHPL2.Service;

import com.sgu.MHPL2.DTO.TrainingPlanDTO;
import com.sgu.MHPL2.DTO.TrainingPlanMapper;
import com.sgu.MHPL2.Model.TrainingPlan;
import com.sgu.MHPL2.Repository.ClassGroupRepository;
import com.sgu.MHPL2.Repository.TrainingPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingPlanService {

    @Autowired
    private TrainingPlanRepository trainingPlanRepository;

    @Autowired
    private ClassGroupRepository classGroupRepository;

    @Autowired
    private TrainingPlanMapper trainingPlanMapper;

    /**
     * Lấy tất cả kế hoạch đào tạo
     * @return Danh sách kế hoạch đào tạo
     */
    public List<TrainingPlanDTO> getAllTrainingPlans() {
        return trainingPlanRepository.findAll().stream()
                .map(trainingPlanMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lấy kế hoạch đào tạo với phân trang và lọc
     * @param semester Học kỳ
     * @param academicYear Năm học
     * @param status Trạng thái
     * @param pageable Thông tin phân trang
     * @return Trang kế hoạch đào tạo đã lọc
     */
    public Page<TrainingPlanDTO> getTrainingPlansByFilters(
            Integer semester, String academicYear, String status, Pageable pageable) {
        return trainingPlanRepository.findByFilters(semester, academicYear, status, pageable)
                .map(trainingPlanMapper::toDTO);
    }

    /**
     * Tìm kiếm kế hoạch đào tạo theo từ khóa
     * @param keyword Từ khóa tìm kiếm
     * @param pageable Thông tin phân trang
     * @return Trang kế hoạch đào tạo thỏa mãn
     */
    public Page<TrainingPlanDTO> searchTrainingPlans(String keyword, Pageable pageable) {
        return trainingPlanRepository.searchByKeyword(keyword, pageable)
                .map(trainingPlanMapper::toDTO);
    }

    /**
     * Lấy thông tin kế hoạch đào tạo theo ID
     * @param id ID kế hoạch đào tạo
     * @return Thông tin kế hoạch đào tạo hoặc null nếu không tồn tại
     */
    public TrainingPlanDTO getTrainingPlanById(Integer id) {
        Optional<TrainingPlan> trainingPlanOptional = trainingPlanRepository.findById(id);
        return trainingPlanOptional.map(trainingPlanMapper::toDTO).orElse(null);
    }

    /**
     * Tạo kế hoạch đào tạo mới
     * @param trainingPlanDTO Thông tin kế hoạch đào tạo cần tạo
     * @return Kế hoạch đào tạo đã tạo
     */
    @Transactional
    public TrainingPlanDTO createTrainingPlan(TrainingPlanDTO trainingPlanDTO) {
        // Thiết lập các giá trị mặc định nếu cần
        if (trainingPlanDTO.getStatus() == null) {
            trainingPlanDTO.setStatus("DRAFT");
        }

        if (trainingPlanDTO.getIsActive() == null) {
            trainingPlanDTO.setIsActive(false);
        }

        // Chuyển DTO sang entity
        TrainingPlan trainingPlan = trainingPlanMapper.toEntity(trainingPlanDTO);
        trainingPlan.setCreatedAt(LocalDateTime.now());
        trainingPlan.setUpdatedAt(LocalDateTime.now());

        // Lưu kế hoạch đào tạo
        TrainingPlan savedTrainingPlan = trainingPlanRepository.save(trainingPlan);

        // Trả về kế hoạch đào tạo đã tạo
        return trainingPlanMapper.toDTO(savedTrainingPlan);
    }

    /**
     * Cập nhật kế hoạch đào tạo hiện có
     * @param id ID kế hoạch đào tạo cần cập nhật
     * @param trainingPlanDTO Thông tin cập nhật
     * @return Kế hoạch đào tạo đã cập nhật hoặc null nếu không tồn tại
     */
    @Transactional
    public TrainingPlanDTO updateTrainingPlan(Integer id, TrainingPlanDTO trainingPlanDTO) {
        if (!trainingPlanRepository.existsById(id)) {
            return null;
        }

        // Lấy ngày tạo từ bản ghi hiện tại
        Optional<TrainingPlan> existingTrainingPlan = trainingPlanRepository.findById(id);
        LocalDateTime createdAt = existingTrainingPlan.get().getCreatedAt();

        // Chuyển DTO sang entity
        TrainingPlan trainingPlan = trainingPlanMapper.toEntity(trainingPlanDTO);
        trainingPlan.setId(id);
        trainingPlan.setCreatedAt(createdAt);
        trainingPlan.setUpdatedAt(LocalDateTime.now());

        // Lưu kế hoạch đào tạo
        TrainingPlan updatedTrainingPlan = trainingPlanRepository.save(trainingPlan);

        // Trả về kế hoạch đào tạo đã cập nhật
        return trainingPlanMapper.toDTO(updatedTrainingPlan);
    }

    /**
     * Xóa kế hoạch đào tạo
     * @param id ID kế hoạch đào tạo cần xóa
     * @return true nếu xóa thành công, false nếu không tồn tại
     */
    @Transactional
    public boolean deleteTrainingPlan(Integer id) {
        if (!trainingPlanRepository.existsById(id)) {
            return false;
        }

        // Kiểm tra xem có nhóm lớp nào đang sử dụng kế hoạch đào tạo này không
        if (classGroupRepository.existsByTrainingPlanId(id)) {
            // Xóa tất cả các nhóm lớp liên quan
            classGroupRepository.deleteByTrainingPlanId(id);
        }

        // Xóa kế hoạch đào tạo
        trainingPlanRepository.deleteById(id);
        return true;
    }

    /**
     * Lấy danh sách kế hoạch đào tạo theo khoa
     * @param department Khoa/Ngành
     * @return Danh sách kế hoạch đào tạo của khoa
     */
    public List<TrainingPlanDTO> getTrainingPlansByDepartment(String department) {
        return trainingPlanRepository.findByDepartment(department).stream()
                .map(trainingPlanMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Kiểm tra xem kế hoạch đào tạo có tồn tại không
     * @param id ID kế hoạch đào tạo
     * @return true nếu tồn tại, false nếu không
     */
    public boolean existsById(Integer id) {
        return trainingPlanRepository.existsById(id);
    }

    /**
     * Duyệt kế hoạch đào tạo
     * @param id ID kế hoạch đào tạo
     * @param approvedBy ID người duyệt
     * @return Kế hoạch đào tạo đã duyệt hoặc null nếu không tồn tại
     */
    @Transactional
    public TrainingPlanDTO approveTrainingPlan(Integer id, Integer approvedBy) {
        Optional<TrainingPlan> trainingPlanOptional = trainingPlanRepository.findById(id);
        if (trainingPlanOptional.isEmpty()) {
            return null;
        }

        TrainingPlan trainingPlan = trainingPlanOptional.get();
        trainingPlan.setStatus("APPROVED");
        trainingPlan.setApprovedBy(approvedBy);
        trainingPlan.setApprovalDate(java.time.LocalDate.now());
        trainingPlan.setUpdatedAt(LocalDateTime.now());

        TrainingPlan updatedTrainingPlan = trainingPlanRepository.save(trainingPlan);
        return trainingPlanMapper.toDTO(updatedTrainingPlan);
    }

    /**
     * Từ chối kế hoạch đào tạo
     * @param id ID kế hoạch đào tạo
     * @param approvedBy ID người từ chối
     * @return Kế hoạch đào tạo đã từ chối hoặc null nếu không tồn tại
     */
    @Transactional
    public TrainingPlanDTO rejectTrainingPlan(Integer id, Integer approvedBy) {
        Optional<TrainingPlan> trainingPlanOptional = trainingPlanRepository.findById(id);
        if (trainingPlanOptional.isEmpty()) {
            return null;
        }

        TrainingPlan trainingPlan = trainingPlanOptional.get();
        trainingPlan.setStatus("REJECTED");
        trainingPlan.setApprovedBy(approvedBy);
        trainingPlan.setApprovalDate(java.time.LocalDate.now());
        trainingPlan.setUpdatedAt(LocalDateTime.now());

        TrainingPlan updatedTrainingPlan = trainingPlanRepository.save(trainingPlan);
        return trainingPlanMapper.toDTO(updatedTrainingPlan);
    }

    /**
     * Kích hoạt kế hoạch đào tạo
     * @param id ID kế hoạch đào tạo
     * @return Kế hoạch đào tạo đã kích hoạt hoặc null nếu không tồn tại
     */
    @Transactional
    public TrainingPlanDTO activateTrainingPlan(Integer id) {
        Optional<TrainingPlan> trainingPlanOptional = trainingPlanRepository.findById(id);
        if (trainingPlanOptional.isEmpty()) {
            return null;
        }

        TrainingPlan trainingPlan = trainingPlanOptional.get();

        // Kiểm tra xem kế hoạch đào tạo đã được duyệt chưa
        if (!"APPROVED".equals(trainingPlan.getStatus())) {
            return null;
        }

        trainingPlan.setIsActive(true);
        trainingPlan.setUpdatedAt(LocalDateTime.now());

        TrainingPlan updatedTrainingPlan = trainingPlanRepository.save(trainingPlan);
        return trainingPlanMapper.toDTO(updatedTrainingPlan);
    }

    /**
     * Hủy kích hoạt kế hoạch đào tạo
     * @param id ID kế hoạch đào tạo
     * @return Kế hoạch đào tạo đã hủy kích hoạt hoặc null nếu không tồn tại
     */
    @Transactional
    public TrainingPlanDTO deactivateTrainingPlan(Integer id) {
        Optional<TrainingPlan> trainingPlanOptional = trainingPlanRepository.findById(id);
        if (trainingPlanOptional.isEmpty()) {
            return null;
        }

        TrainingPlan trainingPlan = trainingPlanOptional.get();
        trainingPlan.setIsActive(false);
        trainingPlan.setUpdatedAt(LocalDateTime.now());

        TrainingPlan updatedTrainingPlan = trainingPlanRepository.save(trainingPlan);
        return trainingPlanMapper.toDTO(updatedTrainingPlan);
    }

    /**
     * Lấy danh sách kế hoạch đào tạo theo năm học và học kỳ
     * @param academicYear Năm học
     * @param semester Học kỳ
     * @return Danh sách kế hoạch đào tạo thỏa mãn
     */
    public List<TrainingPlanDTO> getTrainingPlansByAcademicYearAndSemester(String academicYear, Integer semester) {
        return trainingPlanRepository.findByAcademicYearAndSemester(academicYear, semester).stream()
                .map(trainingPlanMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách kế hoạch đào tạo đang hoạt động
     * @return Danh sách kế hoạch đào tạo đang hoạt động
     */
    public List<TrainingPlanDTO> getActiveTrainingPlans() {
        return trainingPlanRepository.findByIsActiveTrue().stream()
                .map(trainingPlanMapper::toDTO)
                .collect(Collectors.toList());
    }
}
