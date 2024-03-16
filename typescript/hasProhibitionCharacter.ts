boolean hasProhibitionCharacter(word) {

    let len;
    let buff = new Array();
    if (word === null || word === undefined || word === '') {
        return false;
    } else {
        buff = encoding.convert(word, { to: 'SJIS', type: 'array' });
    }

    len = buff.length;

    for (let i = 0; i < len -1; i++){
        // バイトの範囲をチェックする
        if (buff[i] < 0x81 || (buff[i] > 0x9f && buff[i] < 0xe0) || buff[i] > 0xef) {
            continue;
        }

        // 禁則文字チェック
        if (buff[i] == 0x81 &&  buff[i + 1] >= 0xad && buff[i + 1] <= 0xf1) {
            return true;
        }

        if (buff[i] == 0x87 &&  buff[i + 1] >= 0x40 && buff[i + 1] <= 0x9f) {
            return true;
        }

        if (buff[i] == 0xea &&  buff[i + 1] >= 0xa3 && buff[i + 1] <= 0xaf) {
            return true;
        }

        if ((buff[i] == 0xea &&  buff[i + 1] >= 0xb0) && (buff[i] >= 0xeb && buff[i] <= 0xef)) {
            return true;
        }
        i++;
    }

    return false;
}