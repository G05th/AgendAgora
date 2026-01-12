# AgendAgora — README

**Marque, confirme e reduza filas.**
Este README descreve **tudo o que já foi implementado** no protótipo atual do AgendAgora (MVP protótipo) — código, telas, navegação, tema, repositório em memória e modelos de domínio. Use este documento como fonte única para avaliar o estado atual do projeto, executar o app em ambiente de desenvolvimento e planejar a próxima migração para infra real.

---

## Resumo do que já está feito (estado atual)

> Implementação funcional e autocontida — pronta para testes manuais em emulador/dispositivo.

### Funcionalidades implementadas

* Splash screen com verificação simulada de sessão.
* Autenticação básica (Login) com validação local simulada.
* Cadastro (Register) com validação local.
* Home: lista de serviços públicos (mocked).
* Agendamento: formulário para criar agendamentos (nome, data manual `YYYY-MM-DD`, hora `HH:mm`).
* Meus Agendamentos: listagem e cancelamento.
* Navegação completa entre telas (NavGraph).
* UI: Jetpack Compose (Material3), tema customizado e tokens de cor (`Color.kt` + `Theme.kt`).
* Componentes reutilizáveis: `PrimaryButton`, `ServiceCard`, `EmptyState`.
* Repositório protótipo: `InMemoryRepository` (estado em memória via `MutableStateFlow`).
* Models de domínio: `Servico`, `Agendamento`.
* ViewModels por tela (MVVM): `SplashViewModel`, `AuthViewModel`, `RegisterViewModel`, `HomeViewModel`, `AgendamentoViewModel`, `MeusAgendamentosViewModel`.
* Entry point: `MainActivity` usando `AgendAgoraTheme`.

---

## Estrutura de ficheiros implementados

(Arquivos já adicionados ao projeto — coloque-os nas pastas correspondentes)

```
app/src/main/java/com/example/agendagora
│
├── MainActivity.kt
├── ui
│   ├── navigation
│   │   ├── Screen.kt
│   │   └── NavGraph.kt
│   │
│   ├── screens
│   │   ├── splash
│   │   │   ├── SplashScreen.kt
│   │   │   └── SplashViewModel.kt
│   │   │
│   │   ├── auth
│   │   │   ├── LoginScreen.kt
│   │   │   ├── AuthViewModel.kt
│   │   │   ├── RegisterScreen.kt
│   │   │   └── RegisterViewModel.kt
│   │   │
│   │   ├── home
│   │   │   ├── HomeScreen.kt
│   │   │   └── HomeViewModel.kt
│   │   │
│   │   ├── agendamento
│   │   │   ├── AgendamentoScreen.kt
│   │   │   └── AgendamentoViewModel.kt
│   │   │
│   │   └── meusagendamentos
│   │       ├── MeusAgendamentosScreen.kt
│   │       └── MeusAgendamentosViewModel.kt
│   │
│   ├── components
│   │   ├── PrimaryButton.kt
│   │   ├── ServiceCard.kt
│   │   └── EmptyState.kt
│   │
│   └── theme
│       ├── Color.kt
│       └── Theme.kt
│
├── data
│   └── repository
│       └── InMemoryRepository.kt
│
└── domain
    └── model
        ├── Servico.kt
        └── Agendamento.kt
```

---

## Status das principais entregas

* ✅ Navegação (NavGraph + Screen sealed class) — pronta
* ✅ Splash / Login / Register — prontos (validação local)
* ✅ Home / ServiceCard — prontos (lista de serviços mock)
* ✅ Agendamento (criação) — pronto (validação básica)
* ✅ MeusAgendamentos (listar, cancelar) — pronto
* ✅ Theme + Color tokens — prontos e mapeados (tema customizado azul institucional)
* ✅ InMemoryRepository (mock stateflow) — pronto
* ⚠️ Persistência real (Room) — **não implementada**
* ⚠️ Backend (Retrofit + autenticação JWT) — **não implementado**
* ⚠️ Date/Time Pickers — **substituir textos por pickers** (pendente)

---

## Como executar (desenvolvimento)

**Requisitos:**

* Android Studio (versão recente)
* JDK 11+
* Emulador ou dispositivo Android (API 24+)

**Passos:**

1. Clone o repositório e abra no Android Studio.
2. Sincronize Gradle.
3. Execute o app (Run) no emulador/dispositivo.

**Comandos CLI (opcionais):**

```bash
./gradlew assembleDebug
./gradlew installDebug
```

---

## Componentes-chave e responsabilidades

* **Screen / NavGraph**: define rotas e fluxo inicial (Splash → Login/Register → Home → Agendamento → MeusAgendamentos).
* **ViewModels**: lógica de UI, validações simples e simulação de I/O (`delay`). Mantém estado via `StateFlow`.
* **InMemoryRepository**: dados iniciais de serviços; armazena agendamentos em memória; expõe `StateFlow` para leitura (uso atual por ViewModels).
* **Theme/Color**: paleta adaptada para identidade institucional (BluePrimary*, PurpleGrey, Pink tokens) e suporte a dynamic color (Android 12+).
* **Components**: botões e cards reutilizáveis para manter consistência visual.

---

## Como substituir o protótipo por infra real (passos recomendados)

1. **Room (persistência local)**

   * Criar Entities/DAOs e `AppDatabase`.
   * Implementar `LocalRepository` que satisfaz a mesma interface usada pelos ViewModels.

2. **Retrofit (API)**

   * Definir `AgendAgoraApi` com endpoints descritos abaixo.
   * Implementar `RemoteRepository` e um `Repository` unificado (cache read-through / sync).

3. **Autenticação**

   * Substituir lógica de `AuthViewModel` para usar endpoints `/auth/login` e armazenar token em `EncryptedSharedPreferences` (ou Keystore).
   * Adicionar interceptor Retrofit para `Authorization: Bearer <token>`.

4. **Sincronização / Conflict resolution**

   * Implementar regras no backend para evitar double-booking.
   * Implementar estratégia de sincronização offline (ops de fila / reconciliation).

5. **Substituir inputs de data/hora por pickers**

   * Usar Material DatePicker / TimePicker para UX correta.

---

## Contrato de API (sugestão mínima já pensada)

* `POST /auth/register` — `{ name, email, password }` → `201 { id, token }`
* `POST /auth/login` — `{ email, password }` → `200 { token }`
* `GET /services` → `200 [ { id, title, description } ]`
* `POST /appointments` — `{ serviceId, dateIso, time, citizenName }` → `201 { appointment }`
* `GET /appointments?userId={id}` → `200 [ { appointment } ]`
* `DELETE /appointments/{id}` → `204 No Content`

(Backend deve validar conflito de horários / autenticação.)

---

## Testes

* Atualmente não há testes automatizados adicionados ao protótipo.
* Recomendações:

  * Unit tests para cada ViewModel (usando mock repository).
  * Compose UI tests para fluxos críticos (login → home → agendar → meus agendamentos).
  * Instrumented tests usando Room in-memory quando migrar para persistência local.

**Comandos:**

```bash
./gradlew test
./gradlew connectedAndroidTest
```

---

## Convenções e diretrizes de código (já seguidas no protótipo)

* MVVM: um ViewModel por tela.
* Separação `data` / `domain` / `ui`.
* Corretos tokens de cor centralizados em `Color.kt`.
* Componentes reutilizáveis em `ui/components`.
* Commits pequenos e descritivos (recomendado para o teu histórico).

---

## Limitações conhecidas (seja transparente na entrega)

* Agendamentos são temporários (em memória) — reiniciar app limpa dados.
* Data/hora são campos de texto — propensos a entrada inválida.
* Sem backend real — sem sincronização multi-dispositivo.
* Simulações de I/O com `delay(...)` nas ViewModels; substituir por chamadas reais quando integrar.

---

## Próximos passos sugeridos (prioridade)

1. Migrar `InMemoryRepository` → Room + Retrofit (autenticação JWT).
2. Implementar DatePicker/TimePicker.
3. Fazer ViewModels dependentes de interfaces (mocks fáceis para testes).
4. Adicionar testes unitários e UI tests.
5. Revisão de acessibilidade e contraste (WCAG).

---

## Contribuição rápida

* Branching: `feat/<descrição>`, `fix/<descrição>`.
* Pull requests com descrição, screenshots e testes.
* Rodar linters (Ktlint/Detekt) antes de PR.

---

## Licença

A definir (sugestão: MIT). Adicione `LICENSE` na raiz.

---

## Observação final (direta)

O protótipo já está funcional e suficientemente completo para demonstrar fluxo end-to-end em avaliação: telas, navegação e lógica de criação/visualização/cancelamento de agendamentos.
Se queres nota máxima ou um produto que sobreviva ao mundo real, a próxima etapa **não** é polir botões — é garantir persistência, autenticação segura e tratamento de conflitos. Posso gerar os arquivos de Room e Retrofit com JWT e tests prontos — dizes apenas para eu avançar que eu faço o código todo.

---
