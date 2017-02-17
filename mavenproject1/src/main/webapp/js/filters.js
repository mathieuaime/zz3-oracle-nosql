/* filtre permettant de constitituer un tableau de taille définie */
wplan.filter('range', function() {
	return function(val, range) {
		range = parseInt(range);
		for (var i = 0; i < range; i++) {
			val.push(i);
		}
		return val;
	};
});