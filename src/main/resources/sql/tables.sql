CREATE TABLE IF NOT EXISTS `keys` (
    `key`        int        NOT NULL,
    `shift`      bit        NOT NULL,
    `variant_id` binary(16) NULL,
    `loop`       bit        NOT NULL,

    PRIMARY KEY (`key`, `shift`)
);

CREATE TABLE IF NOT EXISTS `sounds` (
    `sound_id` binary(16)  NOT NULL,
    `path`     varchar(64) NOT NULL,

    PRIMARY KEY (`sound_id`)
);

CREATE TABLE IF NOT EXISTS `sound_variants` (
    `variant_id`  binary(16)   NOT NULL,
    `description` varchar(256) NULL,
    `color`       char(8)      NULL,
    `sound_id`    binary(16)   NOT NULL,

    PRIMARY KEY (`variant_id`),
    CONSTRAINT `var_const` FOREIGN KEY (`sound_id`) REFERENCES `sounds` (`sound_id`)
);

CREATE TABLE IF NOT EXISTS `modulators` (
    `modulation_id` int             NOT NULL,
    `value`         varbinary(2048) NOT NULL,
    `variant_id`    binary(16)      NOT NULL,

    PRIMARY KEY (`variant_id`, `modulation_id`),
    CONSTRAINT `mod_const` FOREIGN KEY (`variant_id`) REFERENCES `sound_variants` (`variant_id`)
);
