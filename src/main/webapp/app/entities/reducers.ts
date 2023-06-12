import task from 'app/entities/task/task.reducer';
import registrationEvent from 'app/entities/registration-event/registration-event.reducer';
import agent from 'app/entities/agent/agent.reducer';
import callBack from 'app/entities/call-back/call-back.reducer';
import platform from 'app/entities/platform/platform.reducer';
import taskResult from 'app/entities/task-result/task-result.reducer';
import registrationSecret from 'app/entities/registration-secret/registration-secret.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  task,
  registrationEvent,
  agent,
  callBack,
  platform,
  taskResult,
  registrationSecret,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
