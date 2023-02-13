package kr.ac.kopo.polytable.store.presentation;

import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.store.application.StoreService;
import kr.ac.kopo.polytable.store.dto.CreateStoreRequest;
import kr.ac.kopo.polytable.store.dto.ModifiedStoreRequest;
import kr.ac.kopo.polytable.store.dto.SimpleStoreResponse;
import kr.ac.kopo.polytable.store.dto.StoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<SimpleStoreResponse> create(@Valid @RequestBody CreateStoreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.create(request.toEntity(), this.getPrincipal()));
    }

    @PutMapping
    public ResponseEntity<StoreResponse> modified(@Valid @RequestBody ModifiedStoreRequest request) {
        return ResponseEntity.ok(storeService.modified(request, this.getPrincipal()));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeStore() {
        storeService.removeStore(this.getPrincipal());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<StoreResponse> getMyStoreInfo() {
        return ResponseEntity.ok(storeService.getMyStoreInfo(this.getPrincipal()));
    }

    private CustomUserDetails getPrincipal() {
        log.debug("principal : {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
