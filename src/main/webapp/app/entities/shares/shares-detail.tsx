import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shares.reducer';
import { IShares } from 'app/shared/model/shares.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISharesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SharesDetail extends React.Component<ISharesDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { sharesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.shares.detail.title">Shares</Translate> [<b>{sharesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="companyName">
                <Translate contentKey="accounTalkApp.shares.companyName">Company Name</Translate>
              </span>
            </dt>
            <dd>{sharesEntity.companyName}</dd>
            <dt>
              <span id="numberOfShares">
                <Translate contentKey="accounTalkApp.shares.numberOfShares">Number Of Shares</Translate>
              </span>
            </dt>
            <dd>{sharesEntity.numberOfShares}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.shares.client">Client</Translate>
            </dt>
            <dd>{sharesEntity.client ? sharesEntity.client.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/shares" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/shares/${sharesEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ shares }: IRootState) => ({
  sharesEntity: shares.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SharesDetail);
