package test

import io.kotest.core.spec.style.FunSpec
import fixture.client.Client
import test.action.v1.createAd

fun FunSpec.testApiV1(client: Client) {
    context("v1") {
        test("Create Ad ok") {
            client.createAd()
        }
    }
}