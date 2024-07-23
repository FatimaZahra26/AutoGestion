    package com.AutoStock.AutoStockVersion1.controller;

    import com.AutoStock.AutoStockVersion1.model.Maintenance;
    import com.AutoStock.AutoStockVersion1.service.MaintenanceService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/maintenances")
    public class MaintenanceController {

        @Autowired
        private MaintenanceService maintenanceService;

        @GetMapping
        public List<Maintenance> getAllMaintenances() {
            return maintenanceService.getAllMaintenances();
        }

        @GetMapping("/upcoming")
        public List<Maintenance> getUpcomingMaintenances() {
            return maintenanceService.getUpcomingMaintenances();
        }

        @PostMapping
        public Maintenance addMaintenance(@RequestBody Maintenance maintenance) {
            return maintenanceService.addMaintenance(maintenance);
        }

        @PutMapping("/{id}")
        public Maintenance updateMaintenance(@PathVariable Long id, @RequestBody Maintenance maintenance) {
            maintenance.setId(id);
            return maintenanceService.updateMaintenance(maintenance);
        }

        @DeleteMapping("/{id}")
        public void deleteMaintenance(@PathVariable Long id) {
            maintenanceService.deleteMaintenance(id);
        }
    }
