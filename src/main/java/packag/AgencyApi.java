package packag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import peaksoft.entity.Agency;
import peaksoft.entity.House;
import peaksoft.exception.NonUniqueAgencyNameException;
import peaksoft.service.AgencyService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/agencies")
public class AgencyApi {

    private final AgencyService agencyService;

    @GetMapping
    public String getAllAgency(Model model) {
        model.addAttribute("getAllAgency", agencyService.getAllAgency());
        return "myAgency/getAll";
    }

    @GetMapping("/create")
    public String createAgency(Model model) {
        model.addAttribute("newAgency", new Agency());
        return "myAgency/createAgency";
    }

    @PostMapping("/save")
    public String saveHospital(@ModelAttribute("newAgency")Agency agencies) throws NonUniqueAgencyNameException {
        agencyService.saveAgency(agencies);
        return "redirect:/agencies";
    }

    @DeleteMapping("{agencyID}/delete")
    public String delete(@PathVariable("agencyID") Long id) {
        agencyService.deleteAgency(id);
        return "redirect:/agencies";
    }


    @GetMapping("/get")
    public String searchAgency(
            @RequestParam String parameter,
            @RequestParam String type,
            Model model
    ) {
        Agency agency = agencyService.getAgencyByParameter(parameter, type);
        model.addAttribute("getAgencyById", agency);
        return "myAgency/searchAgency";
    }




    @GetMapping("/{myId}/update")
    public String update(@PathVariable("myId") Long id, Model model) {
        try {
            Agency agencyToUpdate = agencyService.getAgencyById(id);
            model.addAttribute("agencyToUpdate", agencyToUpdate);
        } catch (HttpClientErrorException.NotFound e) {
            return e.getMessage();
        }
        return "myAgency/update-agency";
    }

    @PutMapping("{idToUpdate}/merge")
    public String mergeLesson(
            @PathVariable("idToUpdate") Long id,
            @ModelAttribute("agencyToUpdate") Agency agency) {
        try {
            agencyService.updateAgency(id, agency);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/agencies";
    }

    @GetMapping("/{agencyId}/houseCount")
    public String countHousesForAgency(@PathVariable int agencyId, Model model) {
        Long houseCount = agencyService.countHousesForAgency(agencyId);
        model.addAttribute("houseCount", houseCount);
        return "myAgency/count";

    }

    @GetMapping("/{agencyId}/customerCount")
    public String countCustomersForAgency(@PathVariable int agencyId, Model model) {
        Long customerCount = agencyService.countCustomersForAgency(agencyId);
        model.addAttribute("customerCount", customerCount);
        return "myAgency/count";
    }

}































 /* @GetMapping("/get")
    public String getAgencyById(@RequestParam("agencyId") Long agencyId, Model model) {
        model.addAttribute("getAgencyById", agencyService.getAgencyById(agencyId));
        return "myAgency/getAgency";
    }

    */