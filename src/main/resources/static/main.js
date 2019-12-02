function tr(text) {
    return '<tr>' + text + '</tr>';
}

function td(text) {
    return '<td>' + text + '</td>';
}

function editButton(key) {
    var format = '<button '
        + 'class="btn btn-default" '
        + 'onclick="editKey(\'{key}\')" '
        + '>Edit</button>';
    return format.replace(/{key}/g, key);
}

function deleteButton(key) {
    var format = '<button '
        + 'class="btn btn-default" '
        + 'data-key="{key}" '
        + 'onclick="deleteKey(\'{key}\')" '
        + '>Delete</button>'
    return format.replace(/{key}/g, key);
}

function row(key, value) {
    return $(
        tr(
            td(key) +
            td(value) +
            td(editButton(key)) +
            td(deleteButton(key))));
}

function refreshTable() {
    $.get('/values', function(data) {
        var attr,
            mainTable = $('#mainTable tbody');
        mainTable.empty();
        for (attr in data) {
            if (data.hasOwnProperty(attr)) {
                mainTable.append(row(attr, data[attr]));
            }
        }
    });
}

function editKey(key) {
    var format = '#mainTable tbody td:first-child:contains("{key}")',
        selector = format.replace(/{key}/, key),
        cells = $(selector).parent().children(),
        key = cells[0].textContent,
        value = cells[1].textContent,
        keyInput = $('#keyInput'),
        valueInput = $('#valueInput');

    /* Load the key and value texts into inputs
     * Select value text so it can be directly typed to
     */
    keyInput.val(key);
    valueInput.val(value);
    valueInput.select();
}

function deleteKey(key) {
    /*
     * Delete the key.
     * Reload keys and values in table to reflect the deleted ones.
     * Set keyboard focus to key input: ready to start typing.
     */
    $.post('/delete', {key: key}, function() {
        refreshTable();
        $('#keyInput').focus();
    });
}

$(document).ready(function() {
    var keyInput = $('#keyInput'),
        valueInput = $('#valueInput');

    refreshTable();
    $('#addForm').on('submit', function(event) {
        var data = {
            key: keyInput.val(),
            value: valueInput.val()
        };

        /*
         * Persist the new key-value pair.
         * Clear the inputs.
         * Set keyboard focus to key input: ready to start typing.
         */
        $.post('/add', data, function() {
            refreshTable();
            keyInput.val('');
            valueInput.val('');
            keyInput.focus();
        });
        /* Prevent HTTP form submit */
        event.preventDefault();
    });

    /* Focus keyboard input into key input; ready to start typing */
    keyInput.focus();
});