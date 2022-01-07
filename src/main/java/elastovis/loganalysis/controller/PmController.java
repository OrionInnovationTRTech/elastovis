package elastovis.loganalysis.controller;

import elastovis.loganalysis.document.Pm;
import elastovis.loganalysis.service.map.PmServiceMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pm")
public class PmController {

    private final PmServiceMap pmServiceMap;

    @Autowired
    public PmController(PmServiceMap pmServiceMap) {
        this.pmServiceMap = pmServiceMap;
    }

    @GetMapping("/{id}")
    public Pm findById(@PathVariable final Long id) {
        return pmServiceMap.findById(id);
    }
}
