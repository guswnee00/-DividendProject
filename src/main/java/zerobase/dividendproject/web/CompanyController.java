package zerobase.dividendproject.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    // 자동완성을 위한 API
    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(
            @RequestParam String keyword
    ) {
        return null;
    }

    // 회사 리스트를 조회하는 API
    @GetMapping
    public ResponseEntity<?> searchCompany() {
        return null;
    }

    // 회사 저장
    @PostMapping
    public ResponseEntity<?> addCompany() {
        return null;
    }

    // 회사 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
