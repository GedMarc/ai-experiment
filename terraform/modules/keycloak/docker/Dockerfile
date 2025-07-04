FROM quay.io/keycloak/keycloak:latest AS builder

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true
ENV KC_DB=postgres

WORKDIR /opt/keycloak

# Temporary TLS cert (dev only)
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 \
  -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" \
  -keystore conf/server.keystore

RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:latest
COPY --from=builder /opt/keycloak/ /opt/keycloak/

ENV KC_DB=postgres
ENV KC_DB_URL=jdbc:postgresql://keycloak-db:5432/keycloak
ENV KC_DB_USERNAME=keycloak
ENV KC_DB_PASSWORD=change_me
ENV KC_HOSTNAME=auth.gedmarc.co.za
ENV KC_PROXY=edge
ENV KC_HTTP_ENABLED=true
ENV KC_FEATURES=token-exchange

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start"]