package com.simplon.sondagecs.controller;

import com.simplon.sondagecs.entity.Sondages;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ClientController {

    private RestTemplate restTemplate;

    /**
     * Maps requests for the root URL to the index view. Sends an HTTP GET request to
     * the REST API for a list of all surveys, retrieves the response, and adds the
     * surveys to the model before rendering the index view.
     *
     * @param model the Model object to add the surveys to
     * @return the name of the index view template
     */
    @GetMapping("/")
    public String index(Model model) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rest/sondages";
        ResponseEntity<List<Sondages>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new
                        ParameterizedTypeReference<List<Sondages>>() {
                        });
        List<Sondages> sondages = response.getBody();

        model.addAttribute("sondages", sondages);
        return "index";
    }

    /**
     * Maps requests for the /sondages/{id} URL to the sondages view. Sends an HTTP GET
     * request to the REST API for the survey with the specified ID, retrieves the
     * response, and adds the survey to the model before rendering the sondages view.
     *
     * @param model the Model object to add the survey to
     * @param id    the ID of the survey to retrieve
     * @return the name of the sondages view template
     */
    @GetMapping("/sondages/{id}")
    public String getSondage(Model model, @PathVariable long id) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rest/sondages/{id}";
        ResponseEntity<Sondages> response = restTemplate.getForEntity(url, Sondages.class, id);
        Sondages sondages = response.getBody();

        model.addAttribute("sondages", sondages);
        return "sondages";
    }

    /**
     * Maps requests for the /sondages/form/add URL to the form view. Initializes a new
     * survey with the current date and adds it to the model before rendering the form
     * view.
     *
     * @param model the Model object to add the survey to
     * @return the name of the form view template
     */
    @GetMapping("/sondages/form/add")
    public String formSondage(Model model) {
        Sondages sondages = new Sondages();
        sondages.setStartDate(LocalDate.now()); // Initialize with the current date
        model.addAttribute("sondages", sondages);
        return "form";
    }

    /**
     * Maps requests for the /sondages/maj/{id} URL to the form view. Sends an HTTP GET
     * request to the REST API for the survey with the specified ID, retrieves the
     * response, and adds the survey to the model before rendering the form view.
     *
     * @param model the Model object to add the survey to
     * @param id    the ID of the survey to retrieve
     * @return the name of the form view template
     */
    @GetMapping("/sondages/maj/{id}")
    public String majSondage(Model model, @PathVariable long id) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rest/sondages/{id}";
        ResponseEntity<Sondages> response = restTemplate.getForEntity(url, Sondages.class, id);
        Sondages sondages = response.getBody();

        model.addAttribute("sondages", sondages);
        return "form";
    }

    /**
     * Maps requests for the /sondages/del/{id} URL to a redirect to the root URL.
     * Sends an HTTP DELETE request to the REST API to delete the survey with the
     * specified ID, then redirects the user to the root URL.
     *
     * @param model the Model object (unused)
     * @param id    the ID of the survey to delete
     * @return a redirect to the root URL
     */
    @GetMapping("sondages/del/{id}")
    public String delSondage(Model model, @PathVariable long id) {
        this.restTemplate = new RestTemplate();

        String url = "http://localhost:8080/rest/sondages/{id}";
        restTemplate.delete(url, id);

        return "redirect:/";
    }

    /**
     * Maps POST requests for the /sondages/maj/{id} URL to a redirect to the root URL.
     * Sends an HTTP PUT request to the REST API to update the survey with the specified
     * ID, then redirects the user to the root URL.
     *
     * @param sondages the updated survey to send to the REST API
     * @param id       the ID of the survey to update
     * @return a redirect to the root URL
     */
    @PostMapping("/sondages/maj/{id}")
    public String updateSondage(@ModelAttribute("sondages") Sondages sondages, @PathVariable long id) {

        String url = "http://localhost:8080/rest/sondages/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sondages> request = new HttpEntity<>(sondages, headers);
        ResponseEntity<Sondages> response = restTemplate.exchange(url, HttpMethod.PUT, request, Sondages.class, id);

        return "redirect:/";
    }

    /**
     * Adds a new sondage to the system with the provided information.
     *
     * @param sondages the information of the sondage to add
     * @param model    the model used for the view
     * @return the redirection to the index page if the sondage is successfully added, or the form page with an error message if the start date of the sondage is before today's date
     */
    @PostMapping("/sondages/form/add")
    public String addSondage(@ModelAttribute("sondages") Sondages sondages, Model model) {
        this.restTemplate = new RestTemplate();

        // Check if the start date has passed.
        LocalDate now = LocalDate.now();
        if (sondages.getStartDate().isBefore(now)) {
            model.addAttribute("errorMessage", "La date de début du sondage doit être postérieure ou égale à la date d'aujourd'hui.");
            return "form";
        }

        String url = "http://localhost:8080/rest/sondages";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sondages> request = new HttpEntity<>(sondages, headers);
        ResponseEntity<Sondages> response = restTemplate.postForEntity(url, request, Sondages.class);

        return "redirect:/";
    }
}