package upm.softwaredesign.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import upm.softwaredesign.finalproject.entity.ActorType;
import upm.softwaredesign.finalproject.model.Actor;
import upm.softwaredesign.finalproject.service.ActorService;
import upm.softwaredesign.finalproject.service.BlockchainService;
import upm.softwaredesign.finalproject.viewmodel.CreateOrderVm;
import upm.softwaredesign.finalproject.viewmodel.RequestFormRetailerViewModel;

import java.util.List;

@Controller
public class ActorController {

    private ActorService actorService;
    private BlockchainService blockchainService;

    @Autowired
    public ActorController(ActorService actorService, BlockchainService blockchainService) {
        this.actorService = actorService;
        this.blockchainService = blockchainService;
    }

    @GetMapping("/actor/{id}")
    public ModelAndView getActorDetails(@PathVariable Integer id, ModelAndView mav) {
        Actor actor = actorService.retrieveActorById(id);

        if (actor.getType() == ActorType.RETAILER) {
            List<Actor> factories = actorService.retrieveActorByType(ActorType.FACTORY);
            mav.addObject("factories", factories);
            mav.addObject("selectedFactoryId", 0);
        } else if (actor.getType() == ActorType.FACTORY){
            List<Actor> producers = actorService.retrieveActorByType(ActorType.PRODUCER);
            mav.addObject("producers", producers);
            mav.addObject("selectedProducerId", 0);
        } else {
            List<Actor> factories = actorService.retrieveActorByType(ActorType.FACTORY);
            mav.addObject("factories", factories);
            mav.addObject("selectedFactoryId", 0);
        }

        mav.setViewName(String.format("/actor/%s", actor.getType().getName()));
        mav.addObject("actor", actor);

//        actor.get
//        mav.addObject("")
        return mav;
    }

    @PostMapping("/actor/form")
    public ModelAndView requestForm(ModelAndView mav, RequestFormRetailerViewModel vm) {

        Actor senderActor = actorService.retrieveActorById(vm.getActorId());
        Actor receiverActor = actorService.retrieveActorById(vm.getReceiverId());
        mav.addObject("senderActor", senderActor);
        mav.addObject("receiverActor", receiverActor);
        mav.setViewName("actor/retailerOrder");
        return mav;
    }

    @PostMapping("/actor/newOrder")
    public ModelAndView createOrder(ModelAndView mav, CreateOrderVm orderVm) {
        System.out.println("Creating order for: " + orderVm.getProduct() + " - count: " + orderVm.getCount());
        mav.setViewName("redirect:/");
        return mav;
    }
}
