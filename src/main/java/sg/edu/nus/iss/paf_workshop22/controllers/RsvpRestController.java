package sg.edu.nus.iss.paf_workshop22.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sg.edu.nus.iss.paf_workshop22.exception.RecordNotFoundException;
import sg.edu.nus.iss.paf_workshop22.models.Rsvp;
import sg.edu.nus.iss.paf_workshop22.services.RsvpService;

@RestController
@RequestMapping(path={"/api"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class RsvpRestController {

    @Autowired
    RsvpService rsvpSvc;
    // @Autowired
    // RsvpRepository rsvpRepo;
    
    // GET api/rsvps
    @GetMapping("/rsvps")
    public ResponseEntity<List<Rsvp>> getAllRsvp(){

        // List<Rsvp> rsvpList = rsvpRepo.findAll();
        List<Rsvp> rsvpList = rsvpSvc.findAll();
        return ResponseEntity.ok().body(rsvpList);
    
    }

    // GET api/rsvp?q=name
    // return 404 with error object if rsvp not found
    @GetMapping("/rsvp")
    public ResponseEntity<List<Rsvp>> getRsvpByName(@RequestParam(required = true) String q){

        List<Rsvp> rsvpList = rsvpSvc.findByName(q);

        if (rsvpList.isEmpty()){
            throw new RecordNotFoundException("rsvp",q);
        }else{
            return ResponseEntity.ok().body(rsvpList);
        }
    }
    
    // GET api/rsvp/{email}
    // return 404 with error object if rsvp not found
    @GetMapping("/rsvp/{email}")
    public ResponseEntity<Rsvp> getRsvpByEmail(@PathVariable("email") String email){

        Optional<Rsvp> rsvp = rsvpSvc.findByEmail(email);

        if (rsvp.isEmpty()){
            throw new RecordNotFoundException("rsvp",null,email);
        }else{
            return ResponseEntity.ok().body(rsvp.get());
        }
    }

    // POST /api/rsvp
    // Content-Type: application/x-www-form-urlencoded
    // return 201 if successful
    @PostMapping(path={"/rsvp"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> addRsvp(@Valid @ModelAttribute Rsvp rsvp, BindingResult binding){
    // public ResponseEntity<Boolean> addRsvp(@Valid @RequestBody Rsvp rsvp, BindingResult binding){ // use this if content-type is json

        if(binding.hasErrors()){

            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(false);
              
        }else{
            Boolean saved = rsvpSvc.save(rsvp);

            if(saved){
                return ResponseEntity.status(HttpStatus.CREATED).body(saved);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(saved);
            }
        }
        
    }

    // PUT /api/rsvp/fred@gmail.com
    // Content-Type: application/x-www-form-urlencoded
    // return 201 if successful, 404 if email not found
    @PutMapping(path={"/rsvp/{email}"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> updateRsvp(@PathVariable String email, @ModelAttribute Rsvp rsvp){  // try w/o@ModelAtttribute, try @multivaluemap

            Boolean updated = rsvpSvc.update(email, rsvp);
            
            if(updated){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }else{
                throw new RecordNotFoundException("rsvp", rsvp.getFullname(), email);
            }
            
        }

    // GET /api/rsvps/count
    // return 201 if successful
    @GetMapping("/rsvps/count")
    public ResponseEntity<Integer> getCount(){

        return ResponseEntity.status(HttpStatus.CREATED).body(rsvpSvc.countAll());

    }
}
