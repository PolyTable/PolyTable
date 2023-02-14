package kr.ac.kopo.polytable.store.application;

import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.modelmapper.CustomModelMapper;
import kr.ac.kopo.polytable.store.dto.CreateStoreRequest;
import kr.ac.kopo.polytable.store.dto.ModifiedStoreRequest;
import kr.ac.kopo.polytable.store.dto.SimpleStoreResponse;
import kr.ac.kopo.polytable.store.dto.StoreResponse;
import kr.ac.kopo.polytable.store.error.DuplicateStoreInfoException;
import kr.ac.kopo.polytable.store.error.StoreNotFoundException;
import kr.ac.kopo.polytable.store.model.Store;
import kr.ac.kopo.polytable.store.model.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    private final CustomModelMapper customModelMapper;

    public SimpleStoreResponse create(final Store store, final CustomUserDetails userDetails) {
        if (storeRepository.findByCrn(store.getCrn()).isPresent()) {
            throw new DuplicateStoreInfoException("이미 등록된 사업자등록번호 입니다.");
        }

        if (storeRepository.findByStoreTelNo(store.getStoreTelNo()).isPresent()) {
            throw new DuplicateStoreInfoException("이미 등록된 전화번호 입니다.");
        }

        Member findMember = memberRepository.findById(userDetails.getId()).orElseThrow(MemberNotFoundException::new);
        store.bindingWithOwner(findMember);
        Store savedStore = storeRepository.save(store);

        ModelMapper mapper = customModelMapper.standardMapper();

        return mapper.map(savedStore, SimpleStoreResponse.class);
    }

    public StoreResponse modified(final ModifiedStoreRequest request, final CustomUserDetails userDetails) {
        Member findMember = memberRepository.findById(userDetails.getId()).orElseThrow(MemberNotFoundException::new);
        Store store = storeRepository.findById(findMember.getStore().getId()).orElseThrow(() -> new StoreNotFoundException("업체를 찾을 수 없습니다."));

        if (storeRepository.findByCrn(request.getCrn()).isPresent()) {
            throw new DuplicateStoreInfoException("이미 등록된 사업자등록번호 입니다.");
        }
        if (storeRepository.findByStoreTelNo(request.getStoreTelNo()).isPresent()) {
            throw new DuplicateStoreInfoException("이미 등록된 전화번호 입니다.");
        }


        if (request.getCrn() != null) {
            store.modifiedCrn(request.getCrn());
        }
        if (request.getStoreName() != null) {
            store.modifiedStoreName(request.getStoreName());
        }
        if (request.getStoreTelNo() != null) {
            store.modifiedStoreTelNo(request.getStoreTelNo());
        }
        if (request.getFoundedDate() != null) {
            store.modifiedFoundedDate(request.getFoundedDate());
        }
        if (request.getOpenTime() != null) {
            store.modifiedOpenTime(request.getOpenTime());
        }
        if (request.getCloseTime() != null) {
            store.modifiedCloseTime(request.getCloseTime());
        }
        if (request.getAddress() != null) {
            store.modifiedAddress(request.getAddress());
        }

        Store savedStore = storeRepository.save(store);

        ModelMapper mapper = customModelMapper.standardMapper();

        return mapper.map(savedStore, StoreResponse.class);
    }

    public void removeStore(final CustomUserDetails userDetails) {
        Member findMember = memberRepository.findById(userDetails.getId()).orElseThrow(MemberNotFoundException::new);

        Store findStore = storeRepository.findById(findMember.getStore().getId())
                .orElseThrow(() -> new StoreNotFoundException("존재하지 않는 업체입니다."));

        findMember.removeStoreFromOwner();
        findStore.removeOwner();

        storeRepository.delete(findStore);
    }

    @Transactional(readOnly = true)
    public StoreResponse getMyStoreInfo(final CustomUserDetails userDetails) {
        Member findMember = memberRepository.findById(userDetails.getId()).orElseThrow(MemberNotFoundException::new);

        if (findMember.getStore() == null) {
            throw new StoreNotFoundException("가게를 찾을 수 없습니다.");
        }

        ModelMapper mapper = customModelMapper.standardMapper();

        return mapper.map(storeRepository.findById(findMember.getStore().getId()), StoreResponse.class);
    }
}
