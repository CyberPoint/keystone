import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/task">
        <Translate contentKey="global.menu.entities.task" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/registration-event">
        <Translate contentKey="global.menu.entities.registrationEvent" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/agent">
        <Translate contentKey="global.menu.entities.agent" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/call-back">
        <Translate contentKey="global.menu.entities.callBack" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/platform">
        <Translate contentKey="global.menu.entities.platform" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/task-result">
        <Translate contentKey="global.menu.entities.taskResult" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/registration-secret">
        <Translate contentKey="global.menu.entities.registrationSecret" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
