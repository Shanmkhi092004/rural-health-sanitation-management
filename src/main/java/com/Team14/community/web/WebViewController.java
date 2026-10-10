package com.Team14.community.web;

import com.Team14.community.appointment.AppointmentRepository;
import com.Team14.community.consultation.ConsultationRepository;
import com.Team14.community.healthcentre.HealthCentreRepository;
import com.Team14.community.member3.healthcamp.HealthCampRepository;
import com.Team14.community.member3.issue.IssueRepository;
import com.Team14.community.member3.sanitation.SanitationFacilityRepository;
import com.Team14.community.member3.waterquality.WaterQualityObservationRepository;
import com.Team14.community.repository.MedicineRepository;
import com.Team14.community.resident.ResidentRepository;
import com.Team14.community.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebViewController {

    private final ResidentRepository residentRepository;
    private final HealthCentreRepository healthCentreRepository;
    private final AppointmentRepository appointmentRepository;
    private final ConsultationRepository consultationRepository;
    private final MedicineRepository medicineRepository;
    private final HealthCampRepository healthCampRepository;
    private final IssueRepository issueRepository;
    private final SanitationFacilityRepository sanitationFacilityRepository;
    private final WaterQualityObservationRepository waterQualityObservationRepository;
    private final InventoryService inventoryService;

    public WebViewController(
            ResidentRepository residentRepository,
            HealthCentreRepository healthCentreRepository,
            AppointmentRepository appointmentRepository,
            ConsultationRepository consultationRepository,
            MedicineRepository medicineRepository,
            HealthCampRepository healthCampRepository,
            IssueRepository issueRepository,
            SanitationFacilityRepository sanitationFacilityRepository,
            WaterQualityObservationRepository waterQualityObservationRepository,
            InventoryService inventoryService) {
        this.residentRepository = residentRepository;
        this.healthCentreRepository = healthCentreRepository;
        this.appointmentRepository = appointmentRepository;
        this.consultationRepository = consultationRepository;
        this.medicineRepository = medicineRepository;
        this.healthCampRepository = healthCampRepository;
        this.issueRepository = issueRepository;
        this.sanitationFacilityRepository = sanitationFacilityRepository;
        this.waterQualityObservationRepository = waterQualityObservationRepository;
        this.inventoryService = inventoryService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("pageTitle", "Executive Overview");
        
        // Populate real database counts
        long residentCount = residentRepository.count();
        long healthCentreCount = healthCentreRepository.count();
        long appointmentCount = appointmentRepository.count();
        long consultationCount = consultationRepository.count();
        long medicineCount = medicineRepository.count();
        long healthCampCount = healthCampRepository.count();
        long issueCount = issueRepository.count();
        long sanitationCount = sanitationFacilityRepository.count();
        long waterQualityCount = waterQualityObservationRepository.count();

        model.addAttribute("residentCount", residentCount);
        model.addAttribute("healthCentreCount", healthCentreCount);
        model.addAttribute("appointmentCount", appointmentCount);
        model.addAttribute("consultationCount", consultationCount);
        model.addAttribute("medicineCount", medicineCount);
        model.addAttribute("healthCampCount", healthCampCount);
        model.addAttribute("issueCount", issueCount);
        model.addAttribute("sanitationCount", sanitationCount);
        model.addAttribute("waterQualityCount", waterQualityCount);

        return "dashboard";
    }

    @GetMapping("/residents")
    public String residents(Model model) {
        model.addAttribute("activePage", "residents");
        model.addAttribute("pageTitle", "Resident Directory");
        model.addAttribute("residents", residentRepository.findAll());
        return "residents";
    }

    @GetMapping("/health-centres")
    public String healthCentres(Model model) {
        model.addAttribute("activePage", "health-centres");
        model.addAttribute("pageTitle", "Health Centres");
        model.addAttribute("healthCentres", healthCentreRepository.findAll());
        return "health-centres";
    }

    @GetMapping("/appointments")
    public String appointments(Model model) {
        model.addAttribute("activePage", "appointments");
        model.addAttribute("pageTitle", "Appointments");
        model.addAttribute("appointments", appointmentRepository.findAll());
        model.addAttribute("residents", residentRepository.findAll());
        model.addAttribute("healthCentres", healthCentreRepository.findAll());
        return "appointments";
    }

    @GetMapping("/consultations")
    public String consultations(Model model) {
        model.addAttribute("activePage", "consultations");
        model.addAttribute("pageTitle", "Clinical Consultations");
        model.addAttribute("consultations", consultationRepository.findAll());
        model.addAttribute("residents", residentRepository.findAll());
        model.addAttribute("healthCentres", healthCentreRepository.findAll());
        return "consultations";
    }

    @GetMapping("/inventory")
    public String inventory(Model model) {
        model.addAttribute("activePage", "inventory");
        model.addAttribute("pageTitle", "Medicine Inventory & Supply Chain");
        model.addAttribute("medicines", inventoryService.getAllMedicines());
        model.addAttribute("lowStockMedicines", inventoryService.getLowStockMedicines());
        model.addAttribute("issues", inventoryService.getAllMedicineIssues());
        model.addAttribute("replenishments", inventoryService.getAllReplenishments());
        model.addAttribute("residents", residentRepository.findAll());
        return "inventory";
    }

    @GetMapping("/health-camps")
    public String healthCamps(Model model) {
        model.addAttribute("activePage", "health-camps");
        model.addAttribute("pageTitle", "Health Camps & Outreach");
        model.addAttribute("camps", healthCampRepository.findAll());
        return "health-camps";
    }

    @GetMapping("/community-issues")
    public String communityIssues(Model model) {
        model.addAttribute("activePage", "community-issues");
        model.addAttribute("pageTitle", "Community Issues & Grievances");
        model.addAttribute("issues", issueRepository.findAll());
        return "community-issues";
    }

    @GetMapping("/sanitation")
    public String sanitation(Model model) {
        model.addAttribute("activePage", "sanitation");
        model.addAttribute("pageTitle", "Sanitation Facilities & Hygiene");
        model.addAttribute("facilities", sanitationFacilityRepository.findAll());
        return "sanitation";
    }

    @GetMapping("/water-quality")
    public String waterQuality(Model model) {
        model.addAttribute("activePage", "water-quality");
        model.addAttribute("pageTitle", "Water Quality & Environmental Safety");
        model.addAttribute("observations", waterQualityObservationRepository.findAll());
        return "water-quality";
    }
}
