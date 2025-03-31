package Controller;

import Model.CustomizedPlan;
import Model.CustomizedPlanDAO;
import View.CustomizedPlanView;
import java.util.List;

public class CustomizedPlanController {
    private CustomizedPlanDAO planDAO;
    private CustomizedPlanView planView;

    public CustomizedPlanController(CustomizedPlanView view) {
        this.planDAO = new CustomizedPlanDAO();
        this.planView = view;
    }

    public void registerPlan() {
        CustomizedPlan plan = planView.getPlanDetails();
        try {
            planDAO.createCustomizedPlan(plan);
            planView.displaySuccessMessage("Plan registrado exitosamente");
        } catch (Exception e) {
            planView.displayErrorMessage("Error al registrar el plan: " + e.getMessage());
        }
    }

    public CustomizedPlan getPlanById(int id) {
        try {
            return planDAO.getPlanById(id);
        } catch (Exception e) {
            planView.displayErrorMessage("Error al obtener el plan: " + e.getMessage());
            return null;
        }
    }

    public List<CustomizedPlan> getAllPlans() {
        try {
            return planDAO.getAllCustomizedPlans();
        } catch (Exception e) {
            planView.displayErrorMessage("Error al obtener los planes: " + e.getMessage());
            return null;
        }
    }
}