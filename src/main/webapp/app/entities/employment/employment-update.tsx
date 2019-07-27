import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, reset } from './employment.reducer';
import { IEmployment } from 'app/shared/model/employment.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmploymentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmploymentUpdateState {
  isNew: boolean;
  clientId: string;
}

export class EmploymentUpdate extends React.Component<IEmploymentUpdateProps, IEmploymentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      clientId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getClients();
  }

  saveEntity = (event, errors, values) => {
    values.employmentEndDate = convertDateTimeToServer(values.employmentEndDate);

    if (errors.length === 0) {
      const { employmentEntity } = this.props;
      const entity = {
        ...employmentEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/employment');
  };

  render() {
    const { employmentEntity, clients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.employment.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.employment.home.createOrEditLabel">Create or edit a Employment</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : employmentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="employment-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="employment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="businessNameLabel" for="employment-businessName">
                    <Translate contentKey="accounTalkApp.employment.businessName">Business Name</Translate>
                  </Label>
                  <AvField id="employment-businessName" type="text" name="businessName" />
                </AvGroup>
                <AvGroup>
                  <Label id="payeReferenceLabel" for="employment-payeReference">
                    <Translate contentKey="accounTalkApp.employment.payeReference">Paye Reference</Translate>
                  </Label>
                  <AvField id="employment-payeReference" type="text" name="payeReference" />
                </AvGroup>
                <AvGroup>
                  <Label id="employmentEndDateLabel" for="employment-employmentEndDate">
                    <Translate contentKey="accounTalkApp.employment.employmentEndDate">Employment End Date</Translate>
                  </Label>
                  <AvInput
                    id="employment-employmentEndDate"
                    type="datetime-local"
                    className="form-control"
                    name="employmentEndDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.employmentEntity.employmentEndDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="employment-client">
                    <Translate contentKey="accounTalkApp.employment.client">Client</Translate>
                  </Label>
                  <AvInput id="employment-client" type="select" className="form-control" name="client.id">
                    <option value="" key="0" />
                    {clients
                      ? clients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/employment" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  clients: storeState.client.entities,
  employmentEntity: storeState.employment.entity,
  loading: storeState.employment.loading,
  updating: storeState.employment.updating,
  updateSuccess: storeState.employment.updateSuccess
});

const mapDispatchToProps = {
  getClients,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmploymentUpdate);
