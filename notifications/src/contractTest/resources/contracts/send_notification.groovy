package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Send notification'
    name 'send_notification'

    request {
        method POST()
        url '/v1/notifications/send'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),
                    producer('Bearer test-token')
            )
            header "Content-type", value(producer("Application/json"))
        }
        body(
            text: "test text",
            username: "test username"
        )
    }

    response {
        status NO_CONTENT()
    }
}