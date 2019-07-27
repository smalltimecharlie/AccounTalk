import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/client">
      <Translate contentKey="global.menu.entities.client" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/address">
      <Translate contentKey="global.menu.entities.address" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/employment">
      <Translate contentKey="global.menu.entities.employment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/previous-accountant">
      <Translate contentKey="global.menu.entities.previousAccountant" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/business">
      <Translate contentKey="global.menu.entities.business" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/tax-return">
      <Translate contentKey="global.menu.entities.taxReturn" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/pension-provider">
      <Translate contentKey="global.menu.entities.pensionProvider" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/pension-received">
      <Translate contentKey="global.menu.entities.pensionReceived" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/bank-interest">
      <Translate contentKey="global.menu.entities.bankInterest" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/per-pension-contributions">
      <Translate contentKey="global.menu.entities.perPensionContributions" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/state-pension-received">
      <Translate contentKey="global.menu.entities.statePensionReceived" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/gift-aid-donations">
      <Translate contentKey="global.menu.entities.giftAidDonations" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/bank-details">
      <Translate contentKey="global.menu.entities.bankDetails" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/dividends-received">
      <Translate contentKey="global.menu.entities.dividendsReceived" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/shares">
      <Translate contentKey="global.menu.entities.shares" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/emp-pension-contributions">
      <Translate contentKey="global.menu.entities.empPensionContributions" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/employment-details">
      <Translate contentKey="global.menu.entities.employmentDetails" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/business-profit">
      <Translate contentKey="global.menu.entities.businessProfit" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/earnings">
      <Translate contentKey="global.menu.entities.earnings" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/benefits">
      <Translate contentKey="global.menu.entities.benefits" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/expenses">
      <Translate contentKey="global.menu.entities.expenses" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/eis-investments">
      <Translate contentKey="global.menu.entities.eisInvestments" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
