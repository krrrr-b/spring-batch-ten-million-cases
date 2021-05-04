package com.skeleton.batch.data.config

import org.springframework.data.domain.AuditorAware
import java.util.*

class AuthAuditorAware : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        // @todo. 관리자 페이지를 통한 수정 기록 (증적) 이 필요하다면 기능 수정
        return Optional.of("API")
    }
}
