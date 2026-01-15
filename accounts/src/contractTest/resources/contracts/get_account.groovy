package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Get account by authorization'
    name 'get_owner_by_id'

    request {
        method GET()
        url '/v1/accounts'
        headers {
            header 'Authorization', value(
                    consumer(regex('Bearer\\s+.+')),
                    producer('Bearer test-token')
            )
        }
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                id: '76a655fe-2b69-4ebc-95b4-5329b54cdbc7',
                login: 'test',
                fullname: 'Test Testov',
                birthdate: '1988-10-11',
                amount: 1111
        )
    }
}