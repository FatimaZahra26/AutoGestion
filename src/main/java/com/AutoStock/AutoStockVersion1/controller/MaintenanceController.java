    package com.AutoStock.AutoStockVersion1.controller;

    import com.AutoStock.AutoStockVersion1.model.Maintenance;
    import com.AutoStock.AutoStockVersion1.service.MaintenanceService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.Collections;
    import java.util.List;

    @RestController
    @RequestMapping("/maintenances")
    public class MaintenanceController {

        @Autowired
        private MaintenanceService maintenanceService;

        @GetMapping
        public ResponseEntity<List<Maintenance>> getAllMaintenances() {
            try {
                List<Maintenance> maintenances = maintenanceService.getAllMaintenances();
                return ResponseEntity.ok(maintenances);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Maintenance> getMaintenanceById(@PathVariable Long id) {
            Maintenance maintenance = maintenanceService.getMaintenanceById(id);
            if (maintenance != null) {
                return ResponseEntity.ok(maintenance);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping
        public ResponseEntity<Maintenance> createMaintenance(@RequestBody Maintenance maintenance) {
            Maintenance createdMaintenance = maintenanceService.saveMaintenance(maintenance);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMaintenance);
        }

       /* @PutMapping("/{id}")
        public ResponseEntity<Maintenance> updateMaintenance(@PathVariable Long id, @RequestBody Maintenance maintenance) {
            Maintenance existingMaintenance = maintenanceService.getMaintenanceById(id);
            if (existingMaintenance != null) {
                maintenance.setId(id);
                Maintenance updatedMaintenance = maintenanceService.saveMaintenance(maintenance);
                return ResponseEntity.ok(updatedMaintenance);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
*/
       @PutMapping("/{id}")
       public ResponseEntity<Maintenance> updateMaintenance(@PathVariable Long id, @RequestBody Maintenance maintenance) {
           try {
               Maintenance updatedMaintenance = maintenanceService.updateMaintenance(id, maintenance);
               return ResponseEntity.ok(updatedMaintenance);
           } catch (RuntimeException e) {
               e.printStackTrace(); // Log the exception
               return ResponseEntity.badRequest().body(null); // Return more detailed error if necessary
           }
       }

        @PutMapping("/{id}/completed")
        public ResponseEntity<Maintenance> updateMaintenanceCompletionStatus(@PathVariable Long id, @RequestBody Boolean completed) {
            Maintenance existingMaintenance = maintenanceService.getMaintenanceById(id);
            if (existingMaintenance != null) {
                existingMaintenance.setCompleted(completed);
                Maintenance updatedMaintenance = maintenanceService.saveMaintenance(existingMaintenance);
                return ResponseEntity.ok(updatedMaintenance);
            } else {
                return ResponseEntity.notFound().build();
            }
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
            if (maintenanceService.getMaintenanceById(id) != null) {
                maintenanceService.deleteMaintenance(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }



        }

        @GetMapping("/alerts")
        public ResponseEntity<List<Maintenance>> getUpcomingMaintenances() {
            try {
                List<Maintenance> maintenances = maintenanceService.getUpcomingMaintenances();
                return ResponseEntity.ok(maintenances);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @GetMapping("/pending")
        public ResponseEntity<List<Maintenance>> getPendingMaintenances() {
            try {
                List<Maintenance> maintenances = maintenanceService.getPendingMaintenances();
                return ResponseEntity.ok(maintenances);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    }
