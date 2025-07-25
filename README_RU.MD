# 🎨 PixelBattle — Плагин для Spigot 1.16.5+

> [🇬🇧 Read in English](./README.md)

![PixelBattle Plugin Banner](./banner.png)

PixelBattle — это Minecraft-плагин, который позволяет игрокам устраивать пиксельные баталии, размещая блоки шерсти с задержкой. Учитывает статистику, поддерживает PlaceholderAPI и может логировать действия через CoreProtect.

---

## 🚀 Возможности

- ⏱ Настраиваемая задержка между установками блоков
- 🏆 Таблица лидеров и счётчики
- 🧩 Полная поддержка PlaceholderAPI
- 🛡 Интеграция с CoreProtect (по желанию)
- 🧱 Автоматическая выдача инвентаря с цветной шерстью
- 💾 Хранение данных в YAML

---

## 📥 Установка

1. Скачай `.jar` из [releases](https://github.com/YOUR-USERNAME/PixelBattlePlugin/releases)
2. Помести файл в папку `/plugins` на сервере
3. Установи [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)
4. (Необязательно) Установи [CoreProtect](https://github.com/PlayPro/CoreProtect)
5. Перезапусти сервер

---

## 📜 Команды и Права

| Команда         | Описание                                 | Права           |
|------------------|------------------------------------------|------------------|
| `/setdelay <n>`  | Устанавливает задержку между блоками     | `pb.setdelay`    |

---

## 🧩 PlaceholderAPI

Используй эти плейсхолдеры:

| Placeholder               | Описание                                |
|---------------------------|------------------------------------------|
| `%pixelbattle_delay%`     | Текущая задержка между блоками (в сек.) |
| `%pixelbattle_total%`     | Всего установлено блоков                |
| `%pixelbattle_totalk%`    | То же, но в тысячах                     |
| `%pixelbattle_count%`     | Сколько блоков поставил игрок           |
| `%pixelbattle_{число}%`   | N-й игрок в топе                        |

---

## 🧱 Пример `config.yml`

```yaml
delay: 10
delay-message: "&cПодождите %d секунд!"
placed: "&aВы поставили блок"
entry-format: "&f%s &7- &e%d"
not-a-number: "&cВведённый аргумент не является числом"
setdelay-error: "&cИспользуйте /setdelay <задержка>"
setdelay-success: "&aЗадержка успешно установлена"
```
