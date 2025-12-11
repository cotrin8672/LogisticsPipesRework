# AGENTS.md

このドキュメントは、このリポジトリで作業する **人間開発者** と **AIエージェント** の両方に向けたガイドです。
特に、コード生成系AIが安全かつ一貫したスタイルで変更を行うためのルールをまとめています。

今後、プロジェクトの仕様や方針が固まり次第、ここを更新していってください。

---

## 1. プロジェクト概要

- **種別**
  - Minecraft NeoForge Mod（Kotlinベース）
  - 既存 Mod "Logistics Pipes" を Minecraft 1.21.1 向けに移植する **非公式ポート / リワークプロジェクト**
- **対象バージョン**
  - Minecraft 1.21.1
  - NeoForge（具体的なバージョンは `gradle.properties` の `neoforgeVersion` を参照）
- **主目的**
  1. 長期的には Logistics Pipes のすべての機能を 1.21.1 環境へ移植すること
     - 短期的には、最低限のパイプ類・ブロック類・物流ネットワークの基礎機能を構築する
  2. Kotlin Coroutine・最新の経路探索アルゴリズム・GPU インスタンス描画ライブラリ Mod（Flywheel）などを活用し、
     パフォーマンスとスケーラビリティの観点で元 Mod 以上の実装を目指すこと
- **主要技術スタック**
  - Gradle (Kotlin DSL)
  - Kotlin JVM (toolchain: 21)
  - NeoForge ModDevGradle
  - KotlinForForge (NeoForge 対応版)
  - Kotlin Coroutines（物流ネットワーク処理や経路探索の非同期化に利用予定）
  - Flywheel（GPU インスタンス描画によるパイプ可視化等に利用予定）
- **エントリーポイント**
  - `src/main/kotlin/io/github/cotrin8672/lprework/LogisticsPipesRework.kt`
  - `@Mod(LogisticsPipesRework.ID)` により `modId` と紐づくメインModオブジェクト
- **主な構成ファイル**
  - `build.gradle.kts` : ビルド・依存関係・run構成・publisher 設定
  - `settings.gradle.kts` : pluginManagement と toolchains resolver
  - `gradle.properties` : Minecraft / NeoForge / Mod のバージョンやIDなど
  - `src/main/templates` : mod メタデータ生成用テンプレート（`ProcessResources` で展開）

> NOTE: 元の Logistics Pipes の仕様・ライセンス・挙動互換性ポリシーが固まり次第、ここに補足を追記してください。

---

## 2. ディレクトリ構成の前提

- `src/main/kotlin/` : Kotlin ソースコード
- `src/main/resources/` : 手書きリソース（lang, assets, data など）
- `src/generated/resources/` : データジェネレータ等によって生成されるリソース
- `src/main/templates/` : `ProcessResources` で `build/generated/sources/modMetadata` に展開されるテンプレート

`build.gradle.kts` 末尾の設定:

- `sourceSets.main.get().resources.srcDir("src/generated/resources")`
  - `src/generated/resources` をリソースパスに追加
- `neoForge.ideSyncTask(tasks.processResources)`
  - IDE 同期時に `processResources` を走らせ、テンプレートの展開などを反映

---

## 3. コーディング規約（ドラフト）

- **言語**
  - メイン実装は Kotlin。
  - Java を追加する場合も Kotlin との整合性を意識すること。
- **パッケージ構成**
  - ベースパッケージは `io.github.cotrin8672.lprework`（`gradle.properties` の `modGroupId` に合わせて変更する場合あり）。
- **Mod ID / 定数**
  - `LogisticsPipesRework.ID` など、Mod ID は `const val` で保持し、アノテーションなどに直書きしない。
- **コメント・ドキュメント**
  - 仕様や設計意図が変わりやすい部分は、KDoc あるいはファイル先頭コメントで補足する。

> NOTE: 実際のプロジェクトルールが決まったら、命名規則やレイヤリング（block/item/registry など）をここに明文化してください。

---

## 4. 依存関係の追加ルール（重要）

### 4.1 依存関係の種類

- **実行時・コンパイル時に両方必要なライブラリ**
  - `implementation("group:artifact:version")`
- **開発用・IDE 補完用のみ（実行時は不要）**
  - `compileOnly("group:artifact:version")`
- **ゲーム実行時のみ追加したい Mod / ライブラリ**
  - `localRuntime("group:artifact:version")`
    - `build.gradle.kts` 上で `val localRuntime: Configuration` が定義され、`runtimeClasspath` に `extendsFrom(localRuntime)` されています。
    - これにより、開発環境（runClient など）では読み込まれるが、ビルド済み jar への依存としては含めない、といった使い方が可能です。

### 4.2 追加すべき場所

- すべての依存は `build.gradle.kts` の `dependencies { ... }` ブロックに追加してください。
- 例:
  - KotlinForForge はすでに以下のように指定されています。
    - `implementation("thedarkcolour:kotlinforforge-neoforge:$kotlinForForgeVersion")`
- **AIエージェント向けルール**
  - 依存追加時は、**必ず**どのリポジトリから取得されるかを確認し、必要なら `repositories { ... }` に対応する Maven を追加してください。
  - 既存のリポジトリ:
    - `mavenCentral()`
    - `https://thedarkcolour.github.io/KotlinForForge/`
    - `https://maven.createmod.net`
    - `https://modmaven.dev`

### 4.3 バージョン管理

- できるだけ `val someLibVersion = "x.y.z"` のように **変数でバージョンをまとめる** ことを推奨します。
- `gradle.properties` 側へ移動して `someLibVersion=x.y.z` とし、`build.gradle.kts` から `val someLibVersion: String by project` で参照する形も検討してください。
- Minecraft / NeoForge / KotlinForForge など、Mod 環境に密接なバージョンは、**更新時に互換性を確認してから変更** してください。

---

## 5. NeoForge / ModDevGradle 特有の注意点

- `neoForge { runs { ... } }` で `client`, `server`, `data` の3種類の run 構成が定義されています。
  - ゲーム起動関連の変更は、このブロック内で行うこと。
- `ProcessResources` タスクで `src/main/templates` からテンプレートを展開し、mod メタデータを生成しています。
  - `gradle.properties` で定義された `modId`, `modName`, `modVersion` 等が `expand` によって埋め込まれます。
  - Mod ID などを変更する場合は、**コード・gradle.properties・テンプレートの整合性**をとること。

---

## 6. Publisher 設定

- `com.hypherionmc.modutils.modpublisher` プラグインを使用しています。
- `publisher { ... }` ブロックにて:
  - CurseForge / Modrinth の API キーは環境変数 `CURSE_FORGE_API_KEY`, `MODRINTH_API_KEY` から読み取り。
  - `curseID`, `modrinthID` は実プロジェクトで設定が必要。
  - `curseDepends` / `modrinthDepends` で公開時の依存 Mod を指定（例: `kotlin-for-forge`）。

> NOTE: 実際に公開フローを運用する段階になったら、バージョニングポリシーや changelog 運用ルールをここに整理してください。

---

## 7. AI エージェント向けガイドライン

- **原則**
  - 既存の設定・バージョンを無断で大きく変更しないこと（特に Minecraft / NeoForge / KotlinForForge のバージョン）。
  - 破壊的な変更（パッケージ移動、大量リネームなど）は、ユーザーの指示がない限り提案にとどめること。
- **コード変更時**
  - 1つのコミット / 変更で、関係のない大規模リファクタと機能追加を同時に行わない。
  - テスト・runClient での動作確認が必要な変更（レジストリ追加、ネットワーク、データジェネなど）は、確認手順もコメントまたはPR説明に明記する。
- **依存追加時**
  - どの用途（実行時 / 開発時 / テスト / ローカルのみ）かを明確にし、`implementation` / `compileOnly` / `localRuntime` などを適切に選択する。
  - 新しい Maven リポジトリを追加する場合、その信頼性・用途を簡潔にコメントで残すことを推奨。

---

## 8. 今後追記したい項目（メモ）

- Mod の最終的なコンセプト・機能一覧
- ブロック / アイテム / レジストリのレイヤリング方針
- データパック・タグ・LootTable などの運用ルール
- テスト戦略（ゲーム内テスト、GameTest、単体テスト）
- コントリビュートガイド（ブランチ戦略、PR テンプレート など）

このファイルはドラフトです。プロジェクト概要やルールが固まり次第、積極的に更新してください。
