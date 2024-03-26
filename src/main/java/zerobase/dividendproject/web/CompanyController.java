package zerobase.dividendproject.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import zerobase.dividendproject.model.Company;
import zerobase.dividendproject.persist.entity.CompanyEntity;
import zerobase.dividendproject.service.CompanyService;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // 자동완성을 위한 API
    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword) {
        return null;
    }

    // 회사 리스트를 조회하는 API
    @GetMapping
    public ResponseEntity<?> searchCompany(final Pageable pageable) {
        Page<CompanyEntity> companies = this.companyService.getAllCompany(pageable);
        return ResponseEntity.ok(companies);
    }

    // 회사 및 배당금 정보 저장
    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company request) {
        String ticker = request.getTicker().trim();
        if (ObjectUtils.isEmpty(ticker)) {
            throw new RuntimeException("ticker is empty");
        }

        Company company = this.companyService.save(ticker);

        return ResponseEntity.ok(company);
    }

    // 회사 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
