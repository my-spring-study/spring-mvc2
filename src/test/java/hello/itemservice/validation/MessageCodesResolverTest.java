package hello.itemservice.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        /**
         * 객체 오류의 경우 다음 순서로 2가지 생성
         * 1.: code + "." + object name
         * 2.: code
         *
         * 예) 오류 코드: required, object name: item
         * 1.: required.item
         * 2.: required
         */
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");

        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        /**
         * 필드 오류의 경우 다음 순서로 4가지 메시지 코드 생성
         * 1.: code + "." + object name + "." + field
         * 2.: code + "." + field
         * 3.: code + "." + field type
         * 4.: code
         *
         * 예) 오류 코드: typeMismatch, object name "user", field "age", field type: int
         * 1. "typeMismatch.user.age"
         * 2. "typeMismatch.age"
         * 3. "typeMismatch.int"
         * 4. "typeMismatch"
         */
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);

        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
}
