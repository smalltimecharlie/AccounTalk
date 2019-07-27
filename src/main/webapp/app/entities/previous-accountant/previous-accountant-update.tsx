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
import { getEntity, updateEntity, createEntity, reset } from './previous-accountant.reducer';
import { IPreviousAccountant } from 'app/shared/model/previous-accountant.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPreviousAccountantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPreviousAccountantUpdateState {
  isNew: boolean;
  clientId: string;
}

export class PreviousAccountantUpdate extends React.Component<IPreviousAccountantUpdateProps, IPreviousAccountantUpdateState> {
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
    if (errors.length === 0) {
      const { previousAccountantEntity } = this.props;
      const entity = {
        ...previousAccountantEntity,
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
    this.props.history.push('/entity/previous-accountant');
  };

  render() {
    const { previousAccountantEntity, clients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.previousAccountant.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.previousAccountant.home.createOrEditLabel">
                Create or edit a PreviousAccountant
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : previousAccountantEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="previous-accountant-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="previous-accountant-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="previous-accountant-name">
                    <Translate contentKey="accounTalkApp.previousAccountant.name">Name</Translate>
                  </Label>
                  <AvField id="previous-accountant-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="previous-accountant-email">
                    <Translate contentKey="accounTalkApp.previousAccountant.email">Email</Translate>
                  </Label>
                  <AvField id="previous-accountant-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneNumberLabel" for="previous-accountant-phoneNumber">
                    <Translate contentKey="accounTalkApp.previousAccountant.phoneNumber">Phone Number</Translate>
                  </Label>
                  <AvField id="previous-accountant-phoneNumber" type="text" name="phoneNumber" />
                </AvGroup>
                <AvGroup>
                  <Label for="previous-accountant-client">
                    <Translate contentKey="accounTalkApp.previousAccountant.client">Client</Translate>
                  </Label>
                  <AvInput id="previous-accountant-client" type="select" className="form-control" name="client.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/previous-accountant" replace color="info">
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
  previousAccountantEntity: storeState.previousAccountant.entity,
  loading: storeState.previousAccountant.loading,
  updating: storeState.previousAccountant.updating,
  updateSuccess: storeState.previousAccountant.updateSuccess
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
)(PreviousAccountantUpdate);
