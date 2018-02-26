package metro.ticketing.app.controller;

import metro.ticketing.app.model.RequestStatus;
import metro.ticketing.app.model.Tariff;
import metro.ticketing.app.repository.RequestRepository;
import metro.ticketing.app.service.RequestService;
import metro.ticketing.app.service.TariffService;
import metro.ticketing.app.service.TicketService;
import metro.ticketing.app.view.RequestView;
import metro.ticketing.app.view.TariffView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class MainController {

    private String message = "Hello World";

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private TariffService tariffService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    @RequestMapping(value = {"/requestsList"}, method = RequestMethod.GET)
    public String requestsList(Model model) {
        List<RequestView> requests = new ArrayList<>();

        requestRepository.getAll().forEach(a -> {
            requests.add(new RequestView(a.getId(), a.getRequestStatus(), a.getName(), a.getEmail(), a.getRole().getId()));
        });

        model.addAttribute("requests", requests);

        return "requestsList";
    }

    @RequestMapping(value = {"/createRequest"}, method = RequestMethod.GET)
    public String createRequest(Model model) {


        RequestView requestView = new RequestView();
        model.addAttribute("requestView", requestView);

        return "createRequest";
    }

    @RequestMapping(value = {"/createRequest"}, method = RequestMethod.POST)
    public String createRequest(Model model, @ModelAttribute("requestView") RequestView requestView) {

        try {
            System.out.println(requestView.getName());
            requestView.setRequestStatus(RequestStatus.NEW);
            requestService.createRequest(requestView);
            return "redirect:/createRequest";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid params");
            return "createRequest";
        }
    }

    @RequestMapping(value = {"/asseptRequest/{id}"}, method = RequestMethod.GET)
    public String asseptRequest(@PathVariable Long id) {

        try {
            requestService.acceptRequest(1L, id);
        } catch (Exception e) {
        }

        return "redirect:/requestsList";
    }

    @RequestMapping(value = {"/rejectRequest/{id}"}, method = RequestMethod.GET)
    public String rejectRequest(@PathVariable Long id) {

        try {
            requestService.rejectRequest(1L, id);
        } catch (Exception e) {
        }

        return "redirect:/requestsList";
    }

    @RequestMapping(value = {"/ticketRegistration"}, method = RequestMethod.GET)
    public String ticketRegistration(Model model) {

        TariffView tariffView= new TariffView();
        model.addAttribute("tariffView", tariffView);

        Collection<Tariff> tariffs = tariffService.getTariffs();
        model.addAttribute("tariffs", tariffs);

        return "ticketRegistration";
    }

    @RequestMapping(value = {"/ticketRegistration"}, method = RequestMethod.POST)
    public String ticketRegistration(Model model, @ModelAttribute("tariffView") TariffView tariffView) {

        try {
            ticketService.createTicket(requestRepository.get(1L), tariffView.getId());
            return "redirect:/ticketRegistration";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid params");
            return "ticketRegistration";
        }

    }

}