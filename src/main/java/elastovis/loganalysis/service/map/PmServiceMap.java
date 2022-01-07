package elastovis.loganalysis.service.map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import elastovis.loganalysis.document.Pm;
import elastovis.loganalysis.repository.PmRepository;

@Service
public class PmServiceMap{

    private final PmRepository pmFileRepository;

    @Autowired
    public PmServiceMap(PmRepository pmFileRepository){
        this.pmFileRepository = pmFileRepository;
    }
    public void save(final Pm Pm) {
        pmFileRepository.save(Pm);
    }

    public Pm findById(final Long id) {
        return pmFileRepository.findById(id).orElse(null);
    }

}
