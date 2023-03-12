package sg.edu.nus.iss.paf_workshop22.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.paf_workshop22.models.Rsvp;
import sg.edu.nus.iss.paf_workshop22.repositories.RsvpRepository;

@Service
public class RsvpService {
    
    @Autowired
    RsvpRepository rsvpRepo;

    // Get number of rsvp
    public Integer countAll(){
        return rsvpRepo.countAll();
    }

    // Get All rsvp
    public List<Rsvp> findAll(){
        return rsvpRepo.findAll();
    }

    // Get rsvp by Id
    public Optional<Rsvp> findById(Integer id){

        return rsvpRepo.findById(id);
    }

    // Get rsvp by name
    public List<Rsvp> findByName(String fullname){
       
        return rsvpRepo.findByName(fullname);
    }

    // add new rsvp or overwrite existing rsvp
    // check email, if exist, overwrite the rsvp
    public Boolean save(Rsvp rsvp){

        Optional<Rsvp> record = rsvpRepo.findByEmail(rsvp.getEmail());
        
        if (record.isPresent()){
            return rsvpRepo.update(rsvp);
        }else{
            return rsvpRepo.save(rsvp);
        }
    
    }

    // update rsvp by Email
    public Boolean update(String email,Rsvp rsvp){

        Optional<Rsvp> record = rsvpRepo.findByEmail(email);
        
        if (record.isPresent()){
            return rsvpRepo.update(rsvp);
        }else{
            return false;
        }

    }

    public int[] batchUpdate(List<Rsvp> rsvps){

        return rsvpRepo.batchUpdate(rsvps);
    }

}
