# IQVIATools — Honoraria: Batch Cutoff & Business Lines

**Req 1:** Process tab adds optional **Cutoff (max)** per business line; **FULFILL** / **FULFILL ALL** send optional cutoff amount(s) to Oracle batch generation.

**Req 2:** New **Business Lines** tab — load and save **PO Number** and **Cost Center** for each fulfillment business line.

---

## Changed files (9) — open in GitHub

Base URL: `https://github.com/lavanya-vs/Honoraria-cutoff-PO-/blob/main/`

| # | Path in IQVIATools repo |
|---|-------------------------|
| 1 | [src/main/java/com/iqvia/tools/Honoraria/HonorariaController.java](src/main/java/com/iqvia/tools/Honoraria/HonorariaController.java) |
| 2 | [src/main/java/com/iqvia/tools/Honoraria/HonorariaService.java](src/main/java/com/iqvia/tools/Honoraria/HonorariaService.java) |
| 3 | [src/main/java/com/iqvia/tools/shared/dao/Honoraria/HonorariaRepositoryDAO.java](src/main/java/com/iqvia/tools/shared/dao/Honoraria/HonorariaRepositoryDAO.java) |
| 4 | [src/main/java/com/iqvia/tools/shared/dao/Honoraria/FulfillmentBusinessLineDAO.java](src/main/java/com/iqvia/tools/shared/dao/Honoraria/FulfillmentBusinessLineDAO.java) |
| 5 | [src/main/java/com/iqvia/tools/shared/model/AccountingSchema/FulfillmentBusinessLine.java](src/main/java/com/iqvia/tools/shared/model/AccountingSchema/FulfillmentBusinessLine.java) |
| 6 | [src/main/resources/META-INF/persistence-action.xml](src/main/resources/META-INF/persistence-action.xml) |
| 7 | [src/main/webapp/WEB-INF/views/tools/honoraria/honoraria-main.jsp](src/main/webapp/WEB-INF/views/tools/honoraria/honoraria-main.jsp) |
| 8 | [src/main/webapp/resources/js/tools/honoraria/honoraria.js](src/main/webapp/resources/js/tools/honoraria/honoraria.js) |
| 9 | [src/main/webapp/resources/css/tools/honoraria.css](src/main/webapp/resources/css/tools/honoraria.css) |

---

## Change summary by file (line ranges)

Line numbers refer to the files in **this** repository. **New** = file did not exist in baseline; **Modified** = existing file extended.

### 1. `HonorariaController.java` — Modified

| Lines | Change |
|-------|--------|
| 25 | Import `FulfillmentBusinessLine`. |
| 97–108 | `generate-fulfillment-batch`: optional `@RequestParam("batchCutoffAmount")`; explicit `@RequestParam` names on existing params. |
| 111–123 | `generate-fulfillment-batches`: optional `@RequestParam("batchCutoffAmounts")`; parse cutoffs and pass to service. |
| 125–129 | **New** GET `/fulfillment-business-lines` — list business lines. |
| 131–138 | **New** GET `/save-fulfillment-business-line` — save PO# and cost center by id. |
| 140–163 | **New** `parseBatchCutoffAmounts` helper for FULFILL ALL array. |

### 2. `HonorariaService.java` — Modified

| Lines | Change |
|-------|--------|
| 12, 27, 60 | Wire `FulfillmentBusinessLineDAO` and model. |
| 105–118 | `generateFulfillmentBatch`: accept `Long batchCutoffAmount`; forward to DAO. |
| 122–145 | `generateFulfillmentBatches`: accept `Long[] batchCutoffAmounts`; length validation; forward to DAO. |
| 147–153 | **New** `getFulfillmentBusinessLines` / `saveFulfillmentBusinessLine`. |

### 3. `HonorariaRepositoryDAO.java` — Modified

| Lines | Change |
|-------|--------|
| 46–50 | Oracle `NUMBER10ARRAY` type name constant for JDBC arrays. |
| 456–503 | `generateFulfillmentBatches`: PL/SQL with business-line id array and optional cutoff amount array. |
| 505–548 | `generateFulfillmentBatch`: single-line batch with optional `inBatchCutoffAmount` (9-parameter call). |
| 516–517 | Log cutoff amount on single batch. |
| 800–829 | **New** helpers `toNullableNumber10Array` / array binding for cutoffs and batch ids. |

### 4. `FulfillmentBusinessLineDAO.java` — **New file**

| Lines | Change |
|-------|--------|
| 1–46 | JPA DAO: `findAllOrdered()`; `updatePoAndCostCenter(id, poNumber, costCenter, user)`. |

### 5. `FulfillmentBusinessLine.java` — **New file**

| Lines | Change |
|-------|--------|
| 14–32 | Entity `FULFILLMENT_BUSINESS_LINES`: `BUSINESS_LINE_ID`, `BUSINESS_LINE_DESC`, `BUSINESS_LINE_PO_NUMBER`, `BUSINESS_LINE_COST_CENTER`. |
| 16 | Named query `FulfillmentBusinessLine.findAllOrdered`. |
| 34–64 | Getters/setters. |

### 6. `persistence-action.xml` — Modified

| Lines | Change |
|-------|--------|
| 65 | Register JPA entity `FulfillmentBusinessLine` on persistence unit `puAction`. |

### 7. `honoraria-main.jsp` — Modified

| Lines | Change |
|-------|--------|
| 59 | **New** nav tab **Business Lines** → `#BUSINESS_LINES_TAB`. |
| 132–133 | **New** column **Cutoff (max)** on Process fulfillments table. |
| 139 | Hint text for optional cutoff. |
| 205–234 | **New** Business Lines pane: title, PO/Cost Center table, `#businessLinesTableBody`. |

### 8. `honoraria.js` — Modified

| Lines | Change |
|-------|--------|
| 16 | `lastUnprocessedSummaryRows` for cutoff validation. |
| 55–57 | Load business lines when **Business Lines** tab shown. |
| 73–180 | Cutoff read/validate; `loadFulfillmentBusinessLines` / `saveBusinessLine` API calls. |
| 397–433 | Process search rows: cutoff inputs; **FULFILL ALL** button column. |
| 522–530 | **FULFILL**: validate cutoff; send `batchCutoffAmount`. |
| 567–614 | **FULFILL ALL**: `buildBatchCutoffPayload`; send `batchCutoffAmounts`. |

### 9. `honoraria.css` — Modified

| Lines | Change |
|-------|--------|
| 175–188 | Styles for cutoff hint and cutoff input on Process grid. |
| 197–213 | Styles for Business Lines title, inputs, and SAVE button. |
