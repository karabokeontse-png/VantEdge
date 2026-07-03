# W5 Planning Package v1.0

**Date**: 2026-07-03
**Status**: PLANNING ONLY — Implementation NOT AUTHORIZED

---

## W5 Boundary Definition

W5 begins where P2 ends. The boundary is the `ValidatedDomainObject<T>` returned by P2.

```
Input:  ValidatedDomainObject<T>  (from P2ValidationEngine)
         - result.decision ∈ {Accept, Degraded, Reject}
         - result.failureCodes (populated for Degraded/Reject)
         - result.trace (correlationId, rules, pipelineStage)
         - result.validated (the original validated object)

Output: Scored result (TBD — W5 Planning determines format)
         - Deterministic score derived from validated data
         - Must include P2 correlationId for traceability
```

W5 MUST NOT:
- Re-validate or override P2 decisions
- Modify P2-protected components
- Access raw AI output before P2 validation
- Skip P2 validation for any input

W5 MAY:
- Compute scores from validated data
- Define its own scoring rules and weights
- Add W5-specific trace events (must not collide with P2 event names)

---

## W5 Audit Directive

### Directive W5-AUDIT-001

This directive is to be attached to any future W5 Implementation Request.

**Objective**: Verify that W5 implementation respects P2 Governance boundaries.

**Scope**:
1. No modification to any P2-protected component (see Protected Component Register).
2. All W5 inputs must pass through P2 validation first.
3. W5 scoring must not re-validate or override P2 decisions.
4. P2 correlationId must be preserved in W5 output for traceability.

**Pass Criteria**:
- Zero modifications to P2-protected files.
- All W5 inputs are `ValidatedDomainObject<T>` from P2.
- W5 output references P2 correlationId.

**Fail Criteria**:
- Any P2-protected component modified without revalidation.
- Any bypass of P2 validation in W5 data path.

---

## W5 Acceptance Criteria

W5 is accepted when:

1. **Boundary compliance**: W5 consumes only `ValidatedDomainObject<T>` from P2. No direct access to raw AI output, W4 domain objects, or P2 internals.

2. **Deterministic scoring**: Given identical validated input, W5 produces identical score. No AI calls, no randomness, no external dependencies in the scoring path.

3. **P2 transparency**: W5 output includes the P2 correlationId and decision for every scored item. P2 governance chain is fully traceable through W5.

4. **Zero regression**: All 22 P2 harness tests pass after W5 integration. No P2-protected component behavior changes.

5. **Documentation**: W5 scoring rules, weights, and formulas are documented and version-controlled. No undocumented scoring logic.

---

## W5 Protected Invariants

The following invariants must hold before, during, and after W5 implementation:

| Invariant | Description | Violation Consequence |
|---|---|---|
| P2 is the sole validation gate | All pipeline data passes through P2 before W5 | P2 Governance void |
| P2 decisions are final | W5 must not override Accept→Reject or vice versa | P2 Governance void |
| P2 components unmodified | No changes to protected components without revalidation | P2 Governance void |
| P2 correlationId preserved | Every W5 output traces back to a P2 validation | Audit trail broken |
| Validation before scoring | No scoring without prior validation | Data integrity risk |

---

## W5 Risks

| Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|
| W5 modifies P2 code by accident | Low | Critical — voids P2 governance | Protected Component Register; CI must enforce |
| W5 bypasses P2 for performance | Medium | High — skips validation | Architectural review; W5 audit directive |
| W5 scoring conflicts with P2 decision | Low | High — contradictory outcomes | W5 acceptance criteria mandate P2 decision finality |
| W5 introduces non-determinism | Medium | High — unreproducible scores | Deterministic scoring requirement in acceptance criteria |
| P2 validation rules change → W5 scores drift | Medium | Medium — scores inconsistent with validation intent | Revalidation matrix requires full harness after P2 changes |

---

## W5 Dependencies

| Dependency | Type | Status | Notes |
|---|---|---|---|
| P2ValidationEngine | Internal (governed) | COMPLETE | W5 consumes validated output only |
| P2VerificationHarnessTest | Internal (governed) | COMPLETE | Must pass before/after W5 integration |
| W4 Normalization | Internal | COMPLETE | Produces domain objects validated by P2 |
| Version Store | Internal | EXISTING | Persistence layer for scored results |
| Scoring Rules Definition | Documentation | PLANNING ONLY | Must be defined before implementation |
| Score Data Model | Design | PLANNING ONLY | Must be defined before implementation |

---

## W5 Implementation Prerequisites (Checklist)

Before W5 implementation may begin:

- [ ] P2 Governance Acceptance confirmed (STATUS: ACCEPTED)
- [ ] W5 Boundary Definition reviewed and approved
- [ ] W5 scoring rules documented and reviewed
- [ ] W5 data model defined
- [ ] W5 Acceptance Criteria reviewed
- [ ] Protected Component Register acknowledged by W5 implementer
- [ ] W5 Audit Directive attached to implementation request
- [ ] All 22 P2 harness tests pass on current baseline
