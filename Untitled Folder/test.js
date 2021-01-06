const firstFollow = require('first-follow');

const rules = [
  // S -> a b A
  {
    left: 'S',
    right: ['A']
  },
  {
    left: 'S',
    right: ['B', 'C']
  },

  // A -> b c
  {
    left: 'A',
    right: ['0', 'A', '1']
  },
  {
    left: 'A',
    right: ['B']
  },
  {
    left: 'B',
    right: ['0', 'B', '2']
  },
  {
    left: 'B',
    right: [null]
  },
  {
    left: 'C',
    right: ['2', 'C', '1']
  },
  // A -> ε
  {
    left: 'C',
    right: [null]
  }
];

const { firstSets, followSets, predictSets } = firstFollow(rules);

console.log(firstSets);
/*
 *  // S: a
 *  // A: b, ε
 *
 *  {
 *    S: ['a'],
 *    A: ['b', null]
 *  }
 */


console.log(followSets);
/*
 *  // S: ┤
 *  // A: ┤
 *
 *  {
 *    S: ['\u0000'],
 *    A: ['\u0000']
 *  }
 */

console.log(predictSets);
/*
 *  // 1: a
 *  // 2: b
 *  // 3: ┤
 * 
 *  {
 *    '1': ['a'],
 *    '2': ['b'],
 *    '3': ['\u0000']
 *  }
*/
